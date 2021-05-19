package com.example.nordicmotorhomes.service;

import com.example.nordicmotorhomes.model.Customer;
import com.example.nordicmotorhomes.model.Employee;
import com.example.nordicmotorhomes.model.Person;
import com.example.nordicmotorhomes.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;


    public List<Customer> fetchAllCustomers() {
        return personRepository.fetchAllCustomers();
    }

    public List<Employee> fetchAllEmployees() {
        return personRepository.fetchAllEmployees();
    }

    public Person fetchPersonById(int id) {
        return personRepository.fetchCustomerById(id);
    }

    public Person createNewCustomer(Person person) {
        return personRepository.insertPerson(person);
    }

    public Person updatePerson(Person person) { return personRepository.updatePerson(person); }

    public Person deletePerson(int id) { return personRepository.deletePersonById(id); }
}
