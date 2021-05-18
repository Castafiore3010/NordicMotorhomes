package com.example.nordicmotorhomes.repository;

import com.example.nordicmotorhomes.model.Customer;
import com.example.nordicmotorhomes.model.Employee;
import com.example.nordicmotorhomes.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository  {
    @Autowired
    JdbcTemplate template;


    public List<Customer> fetchAllCustomers() {
        return template.query("SELECT * FROM persons where person_type = 'customer'", new BeanPropertyRowMapper<>(Customer.class));
    }

    public List<Employee> fetchAllEmployees() {
        return template.query("SELECT * FROM persons where person_type = 'staff'", new BeanPropertyRowMapper<>(Employee.class));
    }

    public Person insertPerson(Person person) {

        // insert country
        String country_sql = "INSERT INTO countries (country_name) VALUES ('"+person.getCountry_name()+"')";
        if (checkDuplicateEntry("countries", "country_name", person.getCountry_name()) < 1) {
            template.update(country_sql);
        }

        // insert city
        Integer country_id = countryIdByCountryName(person.getCountry_name());
        String city_sql = "INSERT INTO cities (city_name, country_id) VALUES ('"+person.getCity_name()+"','"+country_id+"')";
        if (checkCityExistsInCountry(person.getCity_name(), country_id) < 1) {
            template.update(city_sql);
        }

        // zipcodes
        Integer city_id = cityIdByCityName(person.getCity_name());
        String zipcode_sql = "INSERT INTO zipcodes (zipcode, city_id) VALUES ('"+person.getZipcode()+"','"+city_id+"')";
        if (zipcodeExistsInCountry(person.getZipcode(),country_id) < 1) {
            template.update(zipcode_sql);
        }


        // addresses
        Integer zipcode_id = zipcodeIdByZipcode(person.getZipcode());
        String address_sql = "INSERT INTO addresses (street_name, zipcode_id) VALUES ('"+person.getStreet_name()+"','"+zipcode_id+"')";
        template.update(address_sql);


        // persons
        Integer address_id = addressIdByAddNameAndZipcodeId(person.getStreet_name(), zipcode_id);
        String person_sql = "INSERT INTO persons (first_name, last_name, email, phonenumber, birthdate, address_id, person_type, person_role)" +
                " VALUES ('"+person.getFirst_name()+"','"+person.getLast_name()+"','"+person.getEmail()+"','"
                +person.getPhoneNumber()+"','"+person.getBirthdate()+"','"+address_id+"','"+person.getPerson_type()+"','"
                +person.getPerson_role()+"')";
        template.update(person_sql);
        return null;
    }

    public Person fetchCustomerById(int id) {
        String sql = "SELECT * from persons join addresses using (address_id) join zipcodes using (zipcode_id) " +
                "join cities using (city_id) join countries using (country_id) WHERE person_id = ?";
        RowMapper<Customer> personRowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer person = template.queryForObject(sql, personRowMapper, id);
        return person;

    }

    public Person updatePerson(Person person){

        boolean countryChanged = false;
        boolean cityChanged = false;
        boolean zipcodeChanged = false;
        boolean addressChanged = false;


        //Countries
        int country_id = person.getCountry_id();
        if (checkDuplicateEntry("countries","country_name",person.getCountry_name()) < 1){ //kontrol af duplicates
            String ins_country = "INSERT INTO countries (country_name) values ('"+person.getCountry_name()+"')";
            template.update(ins_country);
            country_id = countryIdByCountryName(person.getCountry_name());

                String country_sql = "UPDATE countries SET country_name='"+person.getCountry_name()+"' WHERE country_id=" + country_id;
                template.update(country_sql);

        }

        //City
        int city_id = person.getCity_id();

        if (checkCityExistsInCountry(person.getCity_name(),country_id)<1){ //Hvis en by ikke findes i det tilsvarende land:
            String ins_city = "INSERT INTO cities (city_name, country_id) VALUES ('"+person.getCity_name()+"','"+country_id+"')";
            template.update(ins_city);

            city_id = cityIdByCitynameAndCountryId(person.getCity_name(), country_id);

            //String city_sql = "UPDATE cities SET city_name='"+person.getCity_name()+"' WHERE city_id=" + city_id;
            //template.update(city_sql);

        }

        //Zipcodes
        int zipcode_id = person.getZipcode_id();

        if(zipcodeExistsInCountry(person.getZipcode(), country_id)<1){
            String ins_zip = "INSERT INTO zipcodes (zipcode, city_id) VALUES ('"+person.getZipcode()+"','"+city_id+"')";
            template.update(ins_zip);

            zipcode_id = zipcodeIdByZipcodeAndCityId(person.getZipcode(),city_id);

                String zip_sql = "UPDATE zipcodes SET zipcode='"+person.getZipcode()+"', city_id='"+city_id+"' WHERE zipcode_id=" + person.getZipcode_id();
                template.update(zip_sql);

        }

        //Addresses

             /*
            if (countryChanged){
            String country_sql = "UPDATE countries SET country_name='"+person.getCountry_name()+"' WHERE country_id=" + country_id;
            template.update(country_sql);
            }

            if (cityChanged){
            String city_sql = "UPDATE cities SET city_name='"+person.getCity_name()+"' WHERE city_id=" + city_id;
            template.update(city_sql);
            }

            if (zipcodeChanged){
            String zip_sql = "UPDATE zipcodes SET zipcode='"+person.getZipcode()+"', city_id='"+city_id+"' WHERE zipcode_id=" + person.getZipcode_id();
            template.update(zip_sql);
            }


         */

        String addresses_sql = "UPDATE addresses SET street_name='"+person.getStreet_name()+"', zipcode_id='"+zipcode_id+"' WHERE address_id = '" + person.getAddress_id()+"'";
        template.update(addresses_sql);


        //Person
        String persons_sql = "UPDATE persons SET "+
                " first_name='"+person.getFirst_name()+"',"+
                " last_name='"+person.getLast_name()+"', " +
                " email='"+person.getEmail()+"', " +
                " phonenumber='"+person.getPhoneNumber()+"', " +
                " birthdate='"+person.getBirthdate()+"', " +
                " address_id='"+person.getAddress_id()+"', " +
                " person_type='"+person.getPerson_type()+"', " +
                " person_role='"+person.getPerson_role()+"'" +
                " WHERE person_id = "+ person.getPerson_id();

        template.update(persons_sql);
        return person;
    }

    // ID getter methods
    //<editor-fold desc="ID-getters">
    public Integer countryIdByCountryName(String country_name) {
        String sql = "SELECT country_id from countries where country_name = ?";
        Integer country_id = template.queryForObject(sql, Integer.class, country_name);
        return country_id;
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

    public Integer addressIdByAddNameAndZipcodeId(String street_name, Integer zipcode_id) {
        String sql = "SELECT address_id from addresses where street_name = ? and zipcode_id = ?";
        Integer address_id = template.queryForObject(sql, Integer.class,street_name, zipcode_id);
        return address_id;
    }
    //</editor-fold>

    // Duplicate checker methods
    //<editor-fold desc="Duplicate Checkers">
    public Integer checkDuplicateEntry(String table_name, String attributeToCheck, String attributeValue) {
        String sql = "SELECT count(*) from " + table_name + " WHERE " + attributeToCheck + " = ?";
        Integer result = template.queryForObject(sql, Integer.class, attributeValue);
        return result;
    }

    public Integer checkCityExistsInCountry(String city_name, int country_id) {
        String sql = "SELECT count(*) from cities WHERE city_name = ? and country_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, city_name, country_id);
        return result;
    }

    public Integer zipcodeExistsInCountry(String zipcode, int country_id) {
        String sql = "SELECT count(*) FROM zipcodes join cities using (city_id) join countries using (country_id)" +
                " WHERE zipcode = ? and country_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, zipcode, country_id);
        return result;
    }

    public Integer cityIdByCitynameAndCountryId (String city_name, int country_id){
        String sql= "SELECT city_id from cities join countries using (country_id) where city_name = ? and country_id = ?";
        Integer result = template.queryForObject(sql,Integer.class, city_name, country_id);
        return result;
    }

    public Integer zipcodeIdByZipcodeAndCityId(String zipcode, int city_id){
        String sql = "SELECT zipcode_id from zipcodes where zipcode = ? and city_id = ?";
        Integer result = template.queryForObject(sql,Integer.class,zipcode,city_id);
        return result;
    }
    //</editor-fold>  //



}
