package com.example.nordicmotorhomes.controller;

import com.example.nordicmotorhomes.model.Customer;
import com.example.nordicmotorhomes.model.Employee;
import com.example.nordicmotorhomes.model.Motorhome;
import com.example.nordicmotorhomes.model.Person;
import com.example.nordicmotorhomes.service.MotorhomeService;
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
    @Autowired
    MotorhomeService motorhomeService;


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
        personService.createNewPerson(customer);

        return "redirect:/viewAllCustomers";
    }

    @GetMapping("/createEmployee")
    public String createEmployeeLink() {
        return "home/createEmployee";
    }


    @PostMapping("/createEmployee")
    public String createEmployee(@ModelAttribute Employee employee, Model model) {
        model.addAttribute("employee", employee);
        personService.createNewPerson(employee);

        return "redirect:/viewAllEmployees";
    }


    // VIEW ALLS
    @GetMapping("/viewAllCustomers")
    public String viewAllCustomers(Model model) {
        model.addAttribute("customers", personService.fetchAllCustomers());

        return "home/viewAllCustomers";
    }

    @GetMapping("/viewAllEmployees")
    public String viewAllEmployees(Model model) {
    model.addAttribute("employees",personService.fetchAllEmployees());
        return "home/viewAllEmployees";
    }

    @GetMapping("/viewAllMotorhomes")
    public String viewAllMotorhomes(Model model) {
        model.addAttribute("motorhomes", motorhomeService.fetchAllMotorhomes());
        return "home/viewAllMotorhomes";
    }

    // INSPECTS
    @GetMapping("/inspectPersonId={person_id}")
    public String inspectPerson(@PathVariable ("person_id") int id, Model model) {
        Person person = personService.fetchCustomerById(id);
        model.addAttribute("person", person);

        return "home/inspectPerson";
    }

    @GetMapping("/inspectMotorhomeId={motorhome_id}")
    public String inspectMotorhome(@PathVariable ("motorhome_id") int id, Model model) {
        Motorhome motorhome = motorhomeService.findMotorhomeById(id);
        model.addAttribute("motorhome", motorhome);

        return "home/inspectMotorhome";
    }

    // UPDATES
    @GetMapping ("/updatePersonId={person_id}")
    public String updateCustomerButton(@PathVariable ("person_id") int id, Model model){
        Person person = personService.fetchCustomerById(id);
        model.addAttribute("person",person);
        return "home/updatePerson";
    }

    @PostMapping("/updatePerson")
    public String updatePerson(@ModelAttribute Customer person, Model model){
        model.addAttribute("person",person);
        personService.updatePerson(person);
        return "home/index";
    }

    @GetMapping ("/updateMotorhomeId={motorhome_id}")
    public String updateMotorhomeButton(@PathVariable ("motorhome_id") int id, Model model) {
        Motorhome motorhome = motorhomeService.findMotorhomeById(id);
        model.addAttribute("motorhome", motorhome);
        return "home/updateMotorhome";
    }

    @PostMapping("/updateMotorhome")
    public String updateMotorhome(@ModelAttribute Motorhome motorhome, Model model){
        model.addAttribute("motorhome", motorhome);
        motorhomeService.updateMotorhome(motorhome);
        return "home/index";
    }

    @GetMapping("/deletePersonId={person_id}")
    public String deleteCustomer(@PathVariable ("person_id") int id, Model model) {
        Person person = personService.fetchCustomerById(id);
        personService.deletePerson(id);
        if (person.getPerson_type().equalsIgnoreCase("staff")) {
            return "redirect:/viewAllEmployees";
        } else {
            return "redirect:/viewAllCustomers";
        }
    }

}
