package com.example.nordicmotorhomes.repository;
import com.example.nordicmotorhomes.model.InsertRentalContract;
import com.example.nordicmotorhomes.model.Motorhome;
import com.example.nordicmotorhomes.model.RentalContract;
import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
@Repository

public class RentalContractRepository {
    @Autowired
    JdbcTemplate template;

    /**
     * @author Michael
     * @return list of finished to contract.
     * we dont actually use this method yet, but we left it in for the next team
     */
    public List<RentalContract> fetchAllFinishedRentalContracts() { // get all contracts with end before today
        LocalDateTime now = LocalDateTime.now();
        List<RentalContract> rentalContracts = fetchAllRentalContracts();
        List<RentalContract> finishedContracts = new ArrayList<>();

        for (RentalContract rentalContract : rentalContracts) {
            if (rentalContract.getEnd_datetime().isBefore(now)) {
                finishedContracts.add(rentalContract);
            }
        }

        return finishedContracts;

    }

    /**
     * @author Marc
     * @param motorhome used for DML
     * @param start_datetime used for DML
     * @param end_datetime used for DML
     * @return true of false based on count DML
     */
    // checks if motorhome is in contract in specified period between start_datetime and end_datetime,
    // by counting number of rows in database that satisfy where & and clauses
    public boolean motorhomeInContractInPeriod(Motorhome motorhome, LocalDateTime start_datetime,
                                               LocalDateTime end_datetime) {

        String check_sql = "SELECT count(*) from rental_contracts WHERE motorhome_id = "+motorhome.getMotorhome_id() +
                " and ((start_datetime between '" + start_datetime +"' and '"+end_datetime+"')" +
                " or" +
                " (end_datetime between '" + start_datetime+"' and '" + end_datetime+"'))";

        boolean inContract;

        inContract = template.queryForObject(check_sql, Integer.class) > 0;

        return inContract;
    }

    /**
     * @author Emma
     * @return a list of rental contracts
     */
    public List<RentalContract> fetchAllRentalContracts() { // get all contracts in database
        String fetch = "SELECT motorhome_id, person_id, rental_contract_id, start_datetime, end_datetime," +
                " contact_point_pickup_id, contact_point_dropoff_id, cp1.contact_point_name AS pickup_name," +
                " cp1.contact_point_type AS pickup_type, cp2.contact_point_name AS dropoff_name," +
                " cp2.contact_point_type AS dropoff_type, first_name , last_name, email, phonenumber," +
                " model_name, brand_name, capacity, price_id  FROM rental_contracts" +
                " JOIN contact_points cp1 ON rental_contracts.contact_point_pickup_id = cp1.contact_point_id" +
                " JOIN contact_points cp2 ON rental_contracts.contact_point_dropoff_id = cp2.contact_point_id" +
                " JOIN persons using (person_id)" +
                " JOIN motorhomes using(motorhome_id)";
        return template.query(fetch, new BeanPropertyRowMapper<>(RentalContract.class));


    }

    /**
     * @author Samavia
     * @param id used for DML
     * @return Rental COntract object. found by DML
     */
    public RentalContract findContractById(int id){ // get specific contract in database
        String fetch = "SELECT motorhome_id, person_id, rental_contract_id, start_datetime, end_datetime," +
                " contact_point_pickup_id, contact_point_dropoff_id, cp1.contact_point_name AS pickup_name," +
                " cp1.contact_point_type AS pickup_type, cp2.contact_point_name AS dropoff_name," +
                " cp2.contact_point_type AS dropoff_type, first_name , last_name, email, phonenumber," +
                " model_name, brand_name, capacity, price_id  FROM rental_contracts" +
                " JOIN contact_points cp1 ON rental_contracts.contact_point_pickup_id = cp1.contact_point_id" +
                " JOIN contact_points cp2 ON rental_contracts.contact_point_dropoff_id = cp2.contact_point_id" +
                " JOIN persons using (person_id)" +
                " JOIN motorhomes using(motorhome_id)" +
                " WHERE rental_contract_id = ?";
        return template.queryForObject(fetch,new BeanPropertyRowMapper<>(RentalContract.class),id);
    }

    /**
     * @author Michael
     * @param id used for DML
     * @return null
     */
    public RentalContract deleteRentalContractById(int id){ // delete specific contract in database
        String delete_sql = "DELETE FROM rental_contracts WHERE rental_contract_id = ?";
        template.update(delete_sql,id);
        return null;
    }

    /**
     * @author Marc
     * @param rentalContract  used for DML
     * @return null
     */
    public RentalContract updateRentalContract(RentalContract rentalContract){ // update specific contract in database
        String update_sql = "UPDATE rental_contracts SET start_datetime = '"+ rentalContract.getStart_datetime() +
                "', end_datetime = '"+ rentalContract.getEnd_datetime()+
                "', person_id = '"+ rentalContract.getPerson_id() +
                "', motorhome_id = '"+ rentalContract.getMotorhome_id()+
                "', contact_point_pickup_id = '"+ rentalContract.getContact_point_pickup_id()+
                "', contact_point_dropoff_id = '"+ rentalContract.getContact_point_dropoff_id()+
                "' WHERE rental_contract_id = "+ rentalContract.getRental_contract_id();
        template.update(update_sql);

    return null;
    }

    /**
     * @author Emma
     * @param rentalContract used for DML
     * @return null
     */
    public RentalContract insertRentalContract(InsertRentalContract rentalContract) { // insert contract data
        String insert_sql = "INSERT INTO rental_contracts (start_datetime, end_datetime, person_id, motorhome_id, " +
                "contact_point_pickup_id, contact_point_dropoff_id) VALUES (?,?,?,?,?,?)";
        template.update(insert_sql, rentalContract.getStart_datetime(), rentalContract.getEnd_datetime(),
                rentalContract.getPerson_id(), rentalContract.getMotorhome_id(), rentalContract.getPickup_id(),
                rentalContract.getDropoff_id());

        return null;
    }

    /**
     * @author Samavia
     * @param rentalContract used for DML
     * @return Integer, rental contract id
     */
    // ID getter - gets rental_contract_id by short format contracts start, end, person_id and motorhome_id attributes
    public Integer rentalContractIdByStartEndPersonIdMotorhomeId(InsertRentalContract rentalContract) {
        String sql = "SELECT rental_contract_id from rental_contracts WHERE start_datetime = ? and end_datetime = ? and person_id = ? and motorhome_id = ?";
        return template.queryForObject(sql,Integer.class, rentalContract.getStart_datetime().toString(),
                rentalContract.getEnd_datetime().toString(), rentalContract.getPerson_id(), rentalContract.getMotorhome_id());
    }



}
