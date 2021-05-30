package com.example.nordicmotorhomes.controller;

import com.example.nordicmotorhomes.model.*;
import com.example.nordicmotorhomes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    PriceService priceService;


    // HOME PAGE
    @GetMapping("/")
    public String index() {

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

    @GetMapping("/createCustomerFromContract")
    public String createCustomerFromContractButton() {
        return "home/createCustomerFromContract";
    }

    @PostMapping("/createCustomerFromContract")
    public String createCustomerFromContract(@ModelAttribute Customer customer, Model model){
        model.addAttribute("customer", customer);
        personService.createNewPerson(customer);
        return "redirect:/createRentalContract";
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

    @GetMapping("/createMotorhomeFromContract")
    public String createMotorhomeButton() {return "home/createMotorhomeFromContract";}

    @PostMapping("/createMotorhomeFromContract")
    public String createMotorhomeFromContract(@ModelAttribute Motorhome motorhome, Model model) {
        model.addAttribute("motorhome", motorhome);
        motorhomeService.insertMotorhome(motorhome);
        return "redirect:/createRentalContract";
    }



    @PostMapping("/bookMotorhome") // search button in index
    public String searchMotorhome(@ModelAttribute SearchResult searchResult, Model model) {
        // searchResult(start,end, capacity) mapped to model, to hold user specified dates and capacity.
        model.addAttribute("searchResult", searchResult);
        List<Motorhome> motorhomes = motorhomeService.fetchAllMotorhomes(); // all motorhomes in database are fetched
        List<Motorhome> availableMotorhomes = new ArrayList<>(); // new list to hold available motorhomes.

        for (Motorhome motorhome : motorhomes) { // for each
            if (motorhome.getCapacity() >= searchResult.getCapacity() &&
                    !rentalContractService.motorhomeInContractInPeriod(motorhome, searchResult.getStart_datetime(),
                            searchResult.getEnd_datetime())) {
                availableMotorhomes.add(motorhome);
            }
            // if motorhome capacity is equal to, or larger than user specified AND given motorhome is not in a contract
            // in the specified period. (line 110/111)
            // then we add the motorhome to our availableMotorhomes list.
        }
        // the List<Motorhome> availableMotorhomes is mapped to the model as "motorhomes"
        model.addAttribute("motorhomes", availableMotorhomes);

        return "home/bookingPage";
    }

    @GetMapping("/bookMotorhomeId={motorhome_id}/{start}/{end}") // Book now button on bookingPage
    public String bookNowButton(@PathVariable ("motorhome_id") int id,
                                @PathVariable ("start") LocalDateTime start,
                                @PathVariable ("end") LocalDateTime end, Model model) {

        Motorhome motorhome = motorhomeService.findMotorhomeById(id); // Motorhome object is fetched from database via id.

        model.addAttribute("motorhome", motorhome); // fetched Motorhome is mapped to model as "motorhome"
        // all predefined valid contactPoints are fetched as a List<ContactPoint>.
        List<ContactPoint> validPoints = contactPointService.fetchAllValidContactPoints();
        model.addAttribute("contact_points", validPoints); // fetched list is mapped to model as "contact_points"
        // String "Specify Other" is mapped to model as "other".
        // This is done to utilize this option as a part of a <select> dropdown
        String other = "Specify Other";
        model.addAttribute("other", other);
        // SearchResult object is created via PathVariables start and end + the capacity of targeted motorhome.
        SearchResult searchResult = new SearchResult(start, end, motorhome.getCapacity());
        model.addAttribute("searchResult", searchResult); // SearchResult mapped to model as "searchResult"


        return "home/confirmBooking";

    }

    @PostMapping("/confirmBooking")
    public String confirmBooking(@ModelAttribute bookingDetails bookingDetails, Model model) {
        // BookingDetails object is mapped to model as "bookingDetails"
        model.addAttribute("bookingDetails", bookingDetails);
        // new Customer is created with data from BookingDetails object.
        Person customer = new Customer(bookingDetails.getPerson_first_name(), bookingDetails.getPerson_last_name(),
                bookingDetails.getPerson_email(), bookingDetails.getPerson_phone(), bookingDetails.getPerson_birthdate(),
                "customer", "", bookingDetails.getStreet_address(), bookingDetails.getZipcode(),
                bookingDetails.getCity_name(), bookingDetails.getCountry());

        // if specified email exists - update past data with new data
        if (personService.emailExists(customer.getEmail())) {
            personService.updatePerson(customer);
        // else write new data to database
        } else {
            personService.createNewPerson(customer);
        }
        int person_id = personService.personIdByEmail(customer.getEmail()); // id of current customer
        int motorhome_id = bookingDetails.getMotorhome_id(); // id of motorhome to book
        int pickUp_id = bookingDetails.getPickUp_id(); // id of pickup location.
        int dropOff_id = bookingDetails.getDropOff_id(); // id of dropoff location

        if (bookingDetails.getPickUp_id() == 0) { // if "Specify Other" is selected for pickUp
            // Full address format specified by customer
            String fullAddressPickup = bookingDetails.getFullAddress_pickup();
            Geocoder geocoder = new Geocoder(); // Geocoder object
            double[] coordinates = new double[2]; // double[] to hold coordinate values.
            try {
                 coordinates = geocoder.getLatLngFromStreetAdress(fullAddressPickup); // get lat & lng
            } catch (InterruptedException interruptedException) { // no answer from server
                interruptedException.printStackTrace();
            } catch (IOException ioException) { // No result for specified address string
                ioException.printStackTrace();
            }
            String[] address = fullAddressPickup.split(","); // split fullAddress string into String[]
            String street_address = address[0].trim();
            String zipcode = address[1].trim();
            String city_name = address[2].trim();
            // new ContactPoint with newly obtained coordinates
            ContactPoint contactPoint = new ContactPoint(coordinates[0], coordinates[1], street_address, zipcode, city_name);
            // write data to database
            contactPointService.insertContactPoint(contactPoint);
            // update pickUp id to user specified pickUp address.
            pickUp_id = contactPointService.contactPointIdByLatAndlng(coordinates);

        }
        //  Same procedure as for PickUp
        if (bookingDetails.getDropOff_id() == 0) { // if "Specify Other" is selected for Dropoff.
            String fullAddressDropoff = bookingDetails.getFullAddress_dropoff();
            Geocoder geocoder = new Geocoder();
            double[] coordinates = new double[2];
            try {
                coordinates = geocoder.getLatLngFromStreetAdress(fullAddressDropoff);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            String[] address = fullAddressDropoff.split(",");
            String street_address = address[0].trim();
            String zipcode = address[1].trim();
            String city_name = address[2].trim();
            ContactPoint contactPoint = new ContactPoint(coordinates[0], coordinates[1], street_address, zipcode, city_name);
            contactPointService.insertContactPoint(contactPoint);
            dropOff_id = contactPointService.contactPointIdByLatAndlng(coordinates);

        }
        // Short format RentalContract object is created.
        InsertRentalContract rentalContract = new InsertRentalContract(bookingDetails.getStart(), bookingDetails.getEnd(),
                person_id, motorhome_id, pickUp_id, dropOff_id);
        rentalContractService.insertRentalContract(rentalContract); // write data to database
        Motorhome motorhome = motorhomeService.findMotorhomeById(motorhome_id); // fetch Motorhome by id
        int price_id = motorhome.getPrice_id(); // Motorhome price id
        Price price = priceService.findPriceById(price_id); // price object from price id
        // Contract id
        int rental_contract_id = rentalContractService.rentalContractIdByStartEndPersonIdMotorhomeId(rentalContract);
        // Full format RentalContract object is created
        RentalContract rentalContractLong = rentalContractService.findContractById(rental_contract_id);
        model.addAttribute("rentalContract", rentalContractLong); // full format contract added to model
        ContactPoint cpPickUp = contactPointService.findContactPointById(pickUp_id); // pickUp point fetched
        ContactPoint cpDropOff = contactPointService.findContactPointById(dropOff_id); // dropOff point fetched
        List<ContactPoint> validPoints = contactPointService.fetchAllValidContactPoints(); // all points marked as "valid"
        // Calculator is created to calculate prices.
        Calculator calculator = new Calculator(rentalContractLong, price, cpPickUp, cpDropOff, validPoints);

        // Motorhome basecost + seasonal markup
        double motorhomeCost = calculator.calculateMotorhomePriceForPeriod();
        String motorhomeCost1 = String.format("%.2f €", motorhomeCost);
        model.addAttribute("motorhomeCost", motorhomeCost1); // formatted price String added to model

        // Full price for contract including transfercost
        double totalCost = calculator.calculateTotalContractPrice();
        String totalCost1 = String.format("%.2f €", totalCost);
        model.addAttribute("totalPrice", totalCost1); // formatted total added to model
        double transferCost = totalCost - motorhomeCost;
        String transferCost1 = String.format("%.2f €", transferCost);
        model.addAttribute("transferCost", transferCost1); // formatted transfercost mapped to model

        return "home/bookingConfirmed";




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
        rentalContractService.insertRentalContract(rentalContract);
        return "redirect:/viewAllRentalContracts";
    }


    // VIEW ALLS
    @GetMapping("/viewAllCustomers")
    public String viewAllCustomers(Model model) {
        List<Customer> customers = personService.fetchAllCustomers();
        for (Customer customer : customers) {
            if (personService.personInContract(customer.getPerson_id())) {
                customer.setActiveContract(true);
            } else {
                customer.setActiveContract(false);
            }
        }
        model.addAttribute("customers", customers);

        return "home/viewAllCustomers";
    }

    @GetMapping("/viewAllEmployees")
    public String viewAllEmployees(Model model) {
    model.addAttribute("employees",personService.fetchAllEmployees());
        return "home/viewAllEmployees";
    }

    @GetMapping("/viewAllMotorhomes")
    public String viewAllMotorhomes(Model model) {
        List<Motorhome> motorhomes = motorhomeService.fetchAllMotorhomes();
        for (Motorhome motorhome : motorhomes) {
            if (motorhomeService.motorhomeInContract(motorhome.getMotorhome_id())) {
                motorhome.setActiveContract(true);
            } else {
                motorhome.setActiveContract(false);
            }
        }
        model.addAttribute("motorhomes", motorhomes);
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
        if (person.getPerson_type().equalsIgnoreCase("employee")) {
            return "redirect:/viewAllEmployees";
        } else {
            return "redirect:/viewAllCustomers";
        }

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
