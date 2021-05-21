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
}
