package com.example.nordicmotorhomes.repository;
import com.example.nordicmotorhomes.model.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class RentalContractRepository {
    @Autowired
    JdbcTemplate template;


    public List<RentalContract> fetchAllRentalContracts() {
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

    public RentalContract findContractById(int id){
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

    public RentalContract deleteRentalContractById(int id){
        String delete_sql = "DELETE FROM rental_contracts WHERE rental_contract_id = ?";
        template.update(delete_sql,id);
        return null;
    }

    public RentalContract updateRentalContract(RentalContract rentalContract){
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


}
