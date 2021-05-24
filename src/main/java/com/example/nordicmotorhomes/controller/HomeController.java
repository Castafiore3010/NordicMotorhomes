package com.example.nordicmotorhomes.controller;

import com.example.nordicmotorhomes.model.*;
import com.example.nordicmotorhomes.service.ContactPointService;
import com.example.nordicmotorhomes.service.MotorhomeService;
import com.example.nordicmotorhomes.service.PersonService;
import com.example.nordicmotorhomes.service.RentalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class HomeController {


    @Autowired
    PersonService personService;
    @Autowired
    MotorhomeService motorhomeService;
    @Autowired
    RentalContractService rentalContractService;
    @Autowired
    ContactPointService contactPointService;


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

    @GetMapping("/createMotorhome")
    public String createMotorhomeLink(){return "home/createMotorhome";}

    @PostMapping("/createMotorhome")
    public String createMotorhome(@ModelAttribute Motorhome motorhome, Model model) {
        model.addAttribute("motorhome", motorhome);
        motorhomeService.insertMotorhome(motorhome);
        return "redirect:/viewAllMotorhomes";
    }

    @GetMapping("/createRentalContract")
    public String createRentalContractLink(Model model) {
        List<Customer> customers = personService.fetchAllCustomers();
        model.addAttribute("customers", customers);
        List<Motorhome> motorhomes = motorhomeService.fetchAllMotorhomes();
        model.addAttribute("motorhomes", motorhomes);
        List<ContactPoint> contactPoints = contactPointService.fetchAllContactPoints();
        model.addAttribute("contact_points", contactPoints);

        return "home/createRentalContract";
    }

    @PostMapping("/createRentalContract")
    public String createRentalContract(@ModelAttribute InsertRentalContract rentalContract, Model model) {
        model.addAttribute("rentalContract", rentalContract);
        System.out.println(rentalContract.getStart_datetime() + ", " +rentalContract.getEnd_datetime()+ ", " + rentalContract.getPerson_id()+ ", "+ rentalContract.getMotorhome_id()+ ", " + rentalContract.getPickup_id()+ ", " + rentalContract.getDropoff_id());
        rentalContractService.insertRentalContract(rentalContract);
        return "redirect:/viewAllRentalContracts";
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

    @GetMapping("/viewAllRentalContracts")
    public String viewAllRentalContracts(Model model) {
        model.addAttribute("rentalContracts", rentalContractService.fetchAllRentalContracts());
        return "home/viewAllRentalContracts";
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
    @GetMapping("/inspectRentalContractId={rental_contract_id}")
    public String inspectRentalContract(@PathVariable ("rental_contract_id")int id, Model model){
        RentalContract rentalContract = rentalContractService.findContractById(id);
        model.addAttribute("rentalContract",rentalContract);
        return "home/inspectRentalContract";
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
        return "redirect:/viewAllMotorhomes";
    }

    @GetMapping("/updateRentalContractId={rental_contract_id}")
    public String updateRentalContractButton(@PathVariable("rental_contract_id")int id, Model model){
        RentalContract rentalContract = rentalContractService.findContractById(id);
        model.addAttribute("rentalContract",rentalContract);
        return "home/updateRentalContract";
    }


    @PostMapping ("/updateRentalContract")
    public String updateRentalContract(@ModelAttribute RentalContract rentalContract, Model model){
    model.addAttribute("rentalContract",rentalContract);
    rentalContractService.updateRentalContract(rentalContract);

    return "redirect:/viewAllRentalContracts";

    }

    @GetMapping("/updateContactPointId={contact_point_id}/{rental_contract_id}")
    public String updateContactPointButton(@PathVariable ("rental_contract_id") int rental_contract_id, @PathVariable("contact_point_id")int id, Model model){
        ContactPoint contactPoint = contactPointService.findContactPointById(id);
        RentalContract rentalContract = rentalContractService.findContractById(rental_contract_id);
        model.addAttribute("contactPoint",contactPoint);
        model.addAttribute("rentalContract", rentalContract);

        return "home/updateContactPoint";
    }

    @PostMapping("/updateContactPoint")
    public String updateContactPoint(@ModelAttribute ContactPointHelp contactPointHelp, Model model){ // Help object

        ContactPoint contactPoint = new ContactPoint(contactPointHelp.getContact_point_id(),
                contactPointHelp.getContact_point_name(), contactPointHelp.getContact_point_type(),
                contactPointHelp.getStreet_name(),contactPointHelp.getZipcode(),
                contactPointHelp.getCity_name(), contactPointHelp.getAddress_id());
        contactPointService.updateContactPoint(contactPoint);
        contactPoint = contactPointService.findContactPointById(contactPoint.getContact_point_id());
        model.addAttribute("contactPoint", contactPoint);
        int id = contactPointHelp.getRental_contract_id();

        return "redirect:/updateRentalContractId="+id;

    }


    // DELETES
    @GetMapping("/deletePersonId={person_id}")
    public String deleteCustomer(@PathVariable ("person_id") int id) {
        Person person = personService.fetchCustomerById(id);
        personService.deletePerson(id);
        if (person.getPerson_type().equalsIgnoreCase("staff")) {
            return "redirect:/viewAllEmployees";
        } else {
            return "redirect:/viewAllCustomers";
        }
    }
    @GetMapping("/deleteMotorhomeId={motorhome_id}")
    public String deleteMotorhome(@PathVariable ("motorhome_id") int id) {
        motorhomeService.deleteMotorhome(id);
        return "redirect:/viewAllMotorhomes";
    }

    @GetMapping("/deleteRentalContractId={rental_contract_id}")
    public String deleteRentalContract(@PathVariable("rental_contract_id")int id){
        rentalContractService.deleteRentalContractById(id);
        return "redirect:/viewAllRentalContracts";
    }



}
