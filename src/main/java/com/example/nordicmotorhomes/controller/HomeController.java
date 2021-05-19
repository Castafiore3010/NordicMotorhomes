package com.example.nordicmotorhomes.controller;

import com.example.nordicmotorhomes.model.Customer;
import com.example.nordicmotorhomes.model.Employee;
import com.example.nordicmotorhomes.model.Person;
import com.example.nordicmotorhomes.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    PersonService personService;


    // HOME PAGE
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customers", personService.fetchAllCustomers());
        model.addAttribute("employees", personService.fetchAllEmployees());

        return "home/index";
    }

    // CREATES
    @GetMapping("/createCustomer")
        public String createCustomerLink() {
            return "home/createCustomer";
        }

    @PostMapping("/createCustomer")
    public String createCustomer(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("customer", customer);
        personService.createNewCustomer(customer);

        return "redirect:/viewAllCustomers";
    }

    // VIEW ALLS
    @GetMapping("/viewAllCustomers")
    public String viewAllCustomers(Model model) {
        model.addAttribute("customers", personService.fetchAllCustomers());

        return "home/viewAllCustomers";
    }

    // INSPECTS
    @GetMapping("/inspectPersonId={person_id}")
    public String inspectPerson(@PathVariable ("person_id") int id, Model model) {
        Person person = personService.fetchPersonById(id);
        model.addAttribute("person", person);

        return "home/inspectPerson";
    }

    // UPDATES
    @GetMapping ("/updatePersonId={person_id}")
    public String updateCustomerButton(@PathVariable ("person_id") int id, Model model){
        Person person = personService.fetchPersonById(id);
        model.addAttribute("person",person);
        return "home/updatePerson";
    }

    @PostMapping("/updatePerson")
    public String updatePerson(@ModelAttribute Customer person, Model model){
        model.addAttribute("person",person);
        personService.updatePerson(person);
        return "home/index";
    }

    @GetMapping("/deletePersonId={person_id}")
    public String deleteCustomer(@PathVariable ("person_id") int id, Model model) {
        Person person = personService.fetchPersonById(id);
        personService.deletePerson(id);
        if (person.getPerson_type().equalsIgnoreCase("staff")) {
            return "redirect:/viewAllEmployees";
        } else {
            return "redirect:/viewAllCustomers";
        }
    }

}
