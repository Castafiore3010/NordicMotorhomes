package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import java.time.LocalDate;
@Entity
public class Customer extends Person {
    private boolean activeContract;


    public Customer() {}

    public Customer(int person_id, String first_name, String last_name, String email, String phoneNumber,
             LocalDate birthdate, int address_id, String person_type, String person_role, boolean activeContract) {

        super(person_id,first_name,last_name,email,phoneNumber,birthdate,address_id,person_type,person_role);
        this.activeContract = activeContract;

    }

    public boolean isActiveContract() {
        return activeContract;
    }

    public void setActiveContract(boolean activeContract) {
        this.activeContract = activeContract;
    }

}
