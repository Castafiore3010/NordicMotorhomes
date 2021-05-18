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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    PersonService personService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("customers", personService.fetchAllCustomers());
        model.addAttribute("employees", personService.fetchAllEmployees());

        return "home/index";
    }

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

    @GetMapping("/viewAllCustomers")
    public String viewAllCustomers(Model model) {
        model.addAttribute("customers", personService.fetchAllCustomers());

        return "home/viewAllCustomers";
    }
}
