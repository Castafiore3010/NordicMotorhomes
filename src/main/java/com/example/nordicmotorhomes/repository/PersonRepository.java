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


    public List<Customer> fetchAllCustomers() { // get all customers

        return template.query("SELECT * FROM persons where person_type = 'customer'", new BeanPropertyRowMapper<>(Customer.class));
    }

    public List<Employee> fetchAllEmployees() { // get all employees
        return template.query("SELECT * FROM persons where person_type = 'employee'", new BeanPropertyRowMapper<>(Employee.class));
    }

    public boolean personInContract(int id) { // checks if person is in contract (for safe delete)
        String sql = "SELECT count(*) from rental_contracts WHERE person_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, id);
        return result > 0;
    }

    public Person insertPerson(Person person) { // write Person data to database

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
        if (addressExistsInZipcode(person.getStreet_name(), zipcode_id) < 1) {
            template.update(address_sql);
        }


        // persons
        Integer address_id = addressIdByAddNameAndZipcodeId(person.getStreet_name(), zipcode_id);
        String person_sql = "INSERT INTO persons (first_name, last_name, email, phonenumber, birthdate, address_id, person_type, person_role)" +
                " VALUES ('"+person.getFirst_name()+"','"+person.getLast_name()+"','"+person.getEmail()+"','"
                +person.getPhoneNumber()+"','"+person.getBirthdate()+"','"+address_id+"','"+person.getPerson_type()+"','"
                +person.getPerson_role()+"')";
        template.update(person_sql);
        return null;
    }

    public Person fetchCustomerById(int id) { // get specific customer
        String sql = "SELECT * from persons join addresses using (address_id) join zipcodes using (zipcode_id) " +
                "join cities using (city_id) join countries using (country_id) WHERE person_id = ?";
        RowMapper<Customer> personRowMapper = new BeanPropertyRowMapper<>(Customer.class);
        Customer person = template.queryForObject(sql, personRowMapper, id);
        return person;

    }

    public Person fetchEmployeeById(int id) { // get specific employee
        String sql = "SELECT * from persons join addresses using (address_id) join zipcodes using (zipcode_id) " +
                "join cities using (city_Id) join countries using (country_id) where person_id = ?";
        RowMapper<Employee> personRowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee person = template.queryForObject(sql, personRowMapper, id);
        return person;
    }

    public Person updatePerson(Person person){ // update Person data in database

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
        updateAddress(person);
        updateZipcode(person);
        updateCity(person);
        updateCountry(person);
        return person;
    }

    public Person deletePersonById(int id) {
        String delete_sql = "DELETE FROM persons where person_id = " + id;
        template.update(delete_sql);
        return null;

    } // delete person in database

    // ID getter methods - making sure we are referring to the right rows in database
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

    public Integer addressIdByAddNameAndZipcodeId(String street_name, int zipcode_id) {
        String sql = "SELECT address_id from addresses where street_name = ? and zipcode_id = ?";
        Integer address_id = template.queryForObject(sql, Integer.class, street_name, zipcode_id);
        return address_id;
    }
    // checks if email already exists in database. Returns 1 or 0 with our database structure
    public boolean emailExists(String email) {
        String sql = "SELECT count(*) from persons where email = ?";
        return template.queryForObject(sql, Integer.class, email) > 0;
    }
    public Integer personIdByEmail(String email) {
        String sql = "SELECT person_id from persons where email = ?";
        Integer person_id = template.queryForObject(sql, Integer.class, email);
        return person_id;

    }
    //</editor-fold>

    // Duplicate checker methods - for database
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

    public Integer addressExistsInZipcode(String street_name, Integer zipcode_id) {
        String sql = "SELECT count(*) from addresses join zipcodes using (zipcode_id)" +
                " WHERE street_name = ? and zipcode_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, street_name, zipcode_id);
        return result;
    }
    //</editor-fold>  //

    // update other tables - making sure relevant data goes where it belongs.
    //<editor-fold desc="Update methods for relevant tables">
    public Person updateAddress(Person person) {
        String sql = "update addresses set street_name ='"+ person.getStreet_name()+"' WHERE address_id = '"+ person.getAddress_id()+"'";
        template.update(sql);
        return null;
    }

    public Person updateZipcode(Person person) {
        String sql = "update zipcodes set zipcode ='"+ person.getZipcode()+"' WHERE zipcode_id = '"+ person.getZipcode_id()+"'";
        template.update(sql);
        return null;

    }

    public Person updateCountry(Person person) {
        String sql = "update countries set country_name ='"+ person.getCountry_name()+"' WHERE country_id = '"+ person.getCountry_id()+"'";
        template.update(sql);
        return null;

    }

    public Person updateCity(Person person) {
        String sql = "update cities set city_name ='"+ person.getCity_name()+"' WHERE city_id = '"+ person.getCity_id()+"'";
        template.update(sql);
        return null;

    }
    //</editor-fold>

}
