package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public abstract class Person {
    private int country_id;
    private int city_id;
    private int zipcode_id;
    private int address_id;
    @Id
    private int person_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phoneNumber;
    private LocalDate birthdate;
    private String person_type;
    private String person_role;
    private String street_name;
    private String zipcode;
    private String city_name;
    private String country_name;

    public Person() {}


    public Person(int country_id, int city_id, int zipcode_id, int address_id, int person_id, String first_name,
                  String last_name, String email, String phoneNumber, LocalDate birthdate, String person_type,
                  String person_role, String street_name, String zipcode, String city_name, String country_name) {

        this.country_id = country_id;
        this.city_id = city_id;
        this.zipcode_id = zipcode_id;
        this.address_id = address_id;
        this.person_id = person_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.person_type = person_type;
        this.person_role = person_role;
        this.street_name = street_name;
        this.zipcode = zipcode;
        this.city_name = city_name;
        this.country_name = country_name;
    }


    public Person(String first_name, String last_name, String email, String phoneNumber, LocalDate birthdate,
                  String person_type, String person_role, String street_name, String zipcode, String city_name, String country_name) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.person_type = person_type;
        this.person_role = person_role;
        this.street_name = street_name;
        this.zipcode = zipcode;
        this.city_name = city_name;
        this.country_name = country_name;
    }

    //<editor-fold desc="GETTERS N' SETTERS">
    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getZipcode_id() {
        return zipcode_id;
    }

    public void setZipcode_id(int zipcode_id) {
        this.zipcode_id = zipcode_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPerson_type() {
        return person_type;
    }

    public void setPerson_type(String person_type) {
        this.person_type = person_type;
    }

    public String getPerson_role() {
        return person_role;
    }

    public void setPerson_role(String person_role) {
        this.person_role = person_role;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
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

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
    //</editor-fold>





}
