package com.example.nordicmotorhomes.model;


/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
public class ContactPointHelp {
    private int contact_point_id;
    private String contact_point_name;
    private String contact_point_type;
    private String street_name;
    private String zipcode;
    private String city_name;
    private int rental_contract_id;
    private int address_id;




    public ContactPointHelp() {}

    public ContactPointHelp(int contact_point_id, String contact_point_name, String contact_point_type,
                            String street_name, String zipcode, String city_name, int rental_contract_id, int address_id) {

        this.contact_point_id = contact_point_id;
        this.contact_point_name = contact_point_name;
        this.contact_point_type = contact_point_type;
        this.street_name = street_name;
        this.zipcode = zipcode;
        this.city_name = city_name;
        this.rental_contract_id = rental_contract_id;
        this.address_id = address_id;
    }



    public int getContact_point_id() {
        return contact_point_id;
    }

    public void setContact_point_id(int contact_point_id) {
        this.contact_point_id = contact_point_id;
    }

    public String getContact_point_name() {
        return contact_point_name;
    }

    public void setContact_point_name(String contact_point_name) {
        this.contact_point_name = contact_point_name;
    }

    public String getContact_point_type() {
        return contact_point_type;
    }

    public void setContact_point_type(String contact_point_type) {
        this.contact_point_type = contact_point_type;
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

    public int getRental_contract_id() {
        return rental_contract_id;
    }

    public void setRental_contract_id(int rental_contract_id) {
        this.rental_contract_id = rental_contract_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
}
