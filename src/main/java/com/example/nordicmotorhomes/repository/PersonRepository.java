package com.example.nordicmotorhomes.repository;

import com.example.nordicmotorhomes.model.Customer;
import com.example.nordicmotorhomes.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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

}
