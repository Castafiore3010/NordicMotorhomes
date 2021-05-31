package com.example.nordicmotorhomes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingDetails {

    // ids
    private int motorhome_id;
    private int pickUp_id;
    private int dropOff_id;
    // duration data
    private LocalDateTime start;
    private LocalDateTime end;
    // Person master data
    private String person_first_name;
    private String person_last_name;
    private String person_email;
    private String person_phone;
    private LocalDate person_birthdate;
    // address info
    private String street_address;
    private String zipcode;
    private String city_name;
    private String country;
    // contactpoints
    private String fullAddress_pickup;
    private String fullAddress_dropoff;



    public BookingDetails() {}

    public BookingDetails(int motorhome_id, int pickUp_id, int dropOff_id, LocalDateTime start, LocalDateTime end,
                          String person_first_name, String person_last_name, String person_email, String person_phone,
                          LocalDate person_birthdate, String street_address, String zipcode, String city_name,
                          String country, String fullAddress_pickup, String fullAddress_dropoff) {

        this.motorhome_id = motorhome_id;
        this.pickUp_id = pickUp_id;
        this.dropOff_id = dropOff_id;
        this.start = start;
        this.end = end;
        this.person_first_name = person_first_name;
        this.person_last_name = person_last_name;
        this.person_email = person_email;
        this.person_phone = person_phone;
        this.person_birthdate = person_birthdate;
        this.street_address = street_address;
        this.zipcode = zipcode;
        this.city_name = city_name;
        this.country = country;
        this.fullAddress_pickup = fullAddress_pickup;
        this.fullAddress_dropoff = fullAddress_dropoff;
    }

    public int getMotorhome_id() {
        return motorhome_id;
    }

    public void setMotorhome_id(int motorhome_id) {
        this.motorhome_id = motorhome_id;
    }

    public int getPickUp_id() {
        return pickUp_id;
    }

    public void setPickUp_id(int pickUp_id) {
        this.pickUp_id = pickUp_id;
    }

    public int getDropOff_id() {
        return dropOff_id;
    }

    public void setDropOff_id(int dropOff_id) {
        this.dropOff_id = dropOff_id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getPerson_first_name() {
        return person_first_name;
    }

    public void setPerson_first_name(String person_first_name) {
        this.person_first_name = person_first_name;
    }

    public String getPerson_last_name() {
        return person_last_name;
    }

    public void setPerson_last_name(String person_last_name) {
        this.person_last_name = person_last_name;
    }

    public String getPerson_email() {
        return person_email;
    }

    public void setPerson_email(String person_email) {
        this.person_email = person_email;
    }

    public String getPerson_phone() {
        return person_phone;
    }

    public void setPerson_phone(String person_phone) {
        this.person_phone = person_phone;
    }

    public LocalDate getPerson_birthdate() {
        return person_birthdate;
    }

    public void setPerson_birthdate(LocalDate person_birthdate) {
        this.person_birthdate = person_birthdate;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullAddress_pickup() {
        return fullAddress_pickup;
    }

    public void setFullAddress_pickup(String fullAddress_pickup) {
        this.fullAddress_pickup = fullAddress_pickup;
    }

    public String getFullAddress_dropoff() {
        return fullAddress_dropoff;
    }

    public void setFullAddress_dropoff(String fullAddress_dropoff) {
        this.fullAddress_dropoff = fullAddress_dropoff;
    }
}
