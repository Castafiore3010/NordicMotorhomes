package com.example.nordicmotorhomes.repository;


import com.example.nordicmotorhomes.model.ContactPoint;
import com.example.nordicmotorhomes.model.Geocoder;
import com.example.nordicmotorhomes.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class ContactPointRepository {
    @Autowired
    JdbcTemplate template;

    public List<ContactPoint> fetchAllContactPoints() { // get all contactpoints.
        return template.query("SELECT * from contact_points", new BeanPropertyRowMapper<>(ContactPoint.class));
    }

    public List<ContactPoint> fetchAllValidContactPoints() { // get all valid contactpoints
        return template.query("SELECT * FROM contact_points where contact_point_type = 'valid'", new BeanPropertyRowMapper<>(ContactPoint.class));
    }

    public ContactPoint insertContactPoint(ContactPoint contactPoint) { // write data from ContactPoint to database
        Geocoder geocoder = new Geocoder();
        String fullAddress = contactPoint.getStreet_name() + ", " + contactPoint.getCity_name() + ", " + contactPoint.getZipcode();
        String country = "Denmark";
        int country_id = 1;
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
        // if city doesn't exist in country, in the database: write city data to database
        if (checkCityExistsInCountry(contactPoint.getCity_name(), country_id) < 1) {
            String insert_City = "INSERT INTO cities (city_name, country_id) VALUES (?,?)";
            template.update(insert_City, contactPoint.getCity_name(), country_id);
        }
        // if zipcode doesn't already exist in country, in database: write zip data to database
        if (zipcodeExistsInCountry(contactPoint.getZipcode(), country_id) < 1) {
            int city_id = cityIdByCityName(contactPoint.getCity_name());
            String insert_zip = "INSERT INTO zipcodes (zipcode, city_id) VALUES (?,?)";
            template.update(insert_zip, contactPoint.getZipcode(), city_id);
        }
        int zipcode_id = zipcodeIdByZipcode(contactPoint.getZipcode());
        // if street_address doesn't already already exist in zipcode in the database: write address data to database.
        if(addressExistsInZipcode(contactPoint.getStreet_name(), zipcode_id) < 1) {
            String insert_address = "INSERT INTO addresses (street_name, zipcode_id) VALUES (?,?)";
            template.update(insert_address, contactPoint.getStreet_name(), zipcode_id);
        }
        int address_id = addressIdByAddNameAndZipcodeId(contactPoint.getStreet_name(), zipcode_id);
        String contact_point_type = "invalid";
        // write ContactPoint data to database
        String insert_contactPoint = "INSERT INTO contact_points (latitude, longitude, contact_point_name, contact_point_type, address_id) VALUES (?,?,?,?,?)";
        template.update(insert_contactPoint, lat, lng, fullAddress, contact_point_type, address_id);

        return null;



    }
    public ContactPoint updateContactPoint(ContactPoint contactPoint){
        Geocoder geocoder = new Geocoder();
        String fullAddress = contactPoint.getStreet_name() + ", " + contactPoint.getCity_name() + ", " + contactPoint.getZipcode();
        double[] coordinates = new double[2];
        try {
            coordinates = geocoder.getLatLngFromStreetAdress(fullAddress); // get lat + lng from GeoCoder
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

    // checks if city exists in country in database. Returns numOfRows (count(*)) that satisfy the conditions
    public Integer checkCityExistsInCountry(String city_name, int country_id) {
        String sql = "SELECT count(*) from cities WHERE city_name = ? and country_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, city_name, country_id);
        return result;
    }
    // checks if zip exists in country in database. Returns numOfRows (count(*)) that satisfy the conditions
    public Integer zipcodeExistsInCountry(String zipcode, int country_id) {
        String sql = "SELECT count(*) FROM zipcodes join cities using (city_id) join countries using (country_id)" +
                " WHERE zipcode = ? and country_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, zipcode, country_id);
        return result;
    }
    // checks if address exists in zipcode in database. Returns numOfRows (count(*)) that satisfy the conditions
    public Integer addressExistsInZipcode(String street_name, Integer zipcode_id) {
        String sql = "SELECT count(*) from addresses join zipcodes using (zipcode_id)" +
                " WHERE street_name = ? and zipcode_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, street_name, zipcode_id);
        return result;
    }


    public Integer cityIdByCityName(String city_name) {
        String sql = "SELECT city_id from cities where city_name = ?";
        Integer city_id = template.queryForObject(sql, Integer.class, city_name);
        return city_id;
    }

    public Integer zipcodeIdByZipcode(String zipcode) {
        String sql = "SELECT zipcode_id from zipcodes where zipcode = ?";
        Integer zipcode_id = template.queryForObject(sql, Integer.class, zipcode);
        return zipcode_id;

    }

    public Integer addressIdByAddNameAndZipcodeId(String street_name, int zipcode_id) {
        String sql = "SELECT address_id from addresses where street_name = ? and zipcode_id = ?";
        Integer address_id = template.queryForObject(sql, Integer.class,street_name, zipcode_id);
        return address_id;
    }

    public Integer contactPointIdByLatAndLng(double[] coordinates) {
        String sql = "SELECT contact_point_id from contact_points WHERE latitude = ? and longitude = ?";
        return template.queryForObject(sql,Integer.class, coordinates[0], coordinates[1]);
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
