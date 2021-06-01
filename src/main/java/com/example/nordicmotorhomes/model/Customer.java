package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
@Entity
public class Customer extends Person {
    private boolean activeContract;


    public Customer() {}

    public Customer(int country_id, int city_id, int zipcode_id, int address_id, int person_id, String first_name,
                    String last_name, String email, String phoneNumber, LocalDate birthdate, String person_type, String person_role,
                    String street_name, String zipcode, String city_name, String country_name, boolean activeContract) {

        super(country_id, city_id, zipcode_id, address_id, person_id, first_name, last_name, email, phoneNumber,
                birthdate, person_type, person_role, street_name, zipcode, city_name, country_name);
        this.activeContract = activeContract;
    }

    public Customer(String first_name, String last_name, String email, String phoneNumber, LocalDate birthdate,
                    String person_type, String person_role, String street_name, String zipcode, String city_name,
                    String country_name) {
        super(first_name, last_name, email, phoneNumber, birthdate, person_type, person_role, street_name, zipcode, city_name, country_name);
    }

    public boolean isActiveContract() {
        return activeContract;
    }

    public void setActiveContract(boolean activeContract) {
        this.activeContract = activeContract;
    }

}
