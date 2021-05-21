package com.example.nordicmotorhomes.repository;


import com.example.nordicmotorhomes.model.ContactPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ContactPointRepository {
    @Autowired
    JdbcTemplate template;

    public ContactPoint updateContactPoint(ContactPoint contactPoint){

        return null;
    }

    public ContactPoint findContactPointById(int id){
        String sql = "SELECT contact_point_id, latitude, longitude, contact_point_name," +
                " contact_point_type, address_id, street_name, zipcode, city_name " +
                " FROM contact_points" +
                " join addresses using (address_id)" +
                " join zipcodes using (zipcode_id)" +
                " join cities using (city_id)" +
                " WHERE contact_point_id = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<>(ContactPoint.class),id);

    }

}
