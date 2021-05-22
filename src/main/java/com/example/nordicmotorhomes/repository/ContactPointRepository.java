package com.example.nordicmotorhomes.repository;


import com.example.nordicmotorhomes.model.ContactPoint;
import com.example.nordicmotorhomes.model.Geocoder;
import com.example.nordicmotorhomes.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class ContactPointRepository {
    @Autowired
    JdbcTemplate template;

    public ContactPoint updateContactPoint(ContactPoint contactPoint){
        Geocoder geocoder = new Geocoder();
        String fullAddress = contactPoint.getStreet_name() + ", " + contactPoint.getCity_name() + ", " + contactPoint.getZipcode();
        double[] coordinates = new double[2];
        try {
            coordinates = geocoder.getLatLngFromStreetAdress(fullAddress);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double lat = coordinates[0];
        double lng = coordinates[1];

        String sql ="UPDATE contact_points SET latitude = '" +lat+ "', longitude = '"+lng+"'," +
                " contact_point_name = '"+contactPoint.getContact_point_name()+"', " +
                " contact_point_type = '"+contactPoint.getContact_point_type()+"'" +
                " WHERE contact_point_id = '"+contactPoint.getContact_point_id()+"'";
        String test_sql = "SELECT zipcode_id from addresses join zipcodes using (zipcode_id) join cities using (city_id) " +
                " WHERE address_id = "+contactPoint.getAddress_id();
        Integer zipcode_id = template.queryForObject(test_sql, Integer.class);
        String zip_sql = "update zipcodes set zipcode ='"+ contactPoint.getZipcode()+"' WHERE zipcode_id = '"+ zipcode_id+"'";
        template.update(sql); // contact_points
        updateAddress(contactPoint); // address
        template.update(zip_sql); // zip
        updateCity(contactPoint); // city


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


    // update other tables
    //<editor-fold desc="Update methods for relevant tables">
    public ContactPoint updateAddress(ContactPoint contactPoint) {
        String sql = "update addresses set street_name ='"+ contactPoint.getStreet_name()+"' WHERE address_id = '"+ contactPoint.getAddress_id()+"'";
        template.update(sql);
        return null;
    }



    public ContactPoint updateCity(ContactPoint contactPoint) {
        String select_sql = "SELECT city_id from addresses join zipcodes using (zipcode_id) join cities using (city_id) WHERE address_id = ?";
        int city_id = template.queryForObject(select_sql, Integer.class, contactPoint.getAddress_id());
        String sql = "update cities set city_name ='"+ contactPoint.getCity_name()+"' WHERE city_id = '"+city_id+"'";
        template.update(sql);
        return null;

    }
    //</editor-fold>

}
