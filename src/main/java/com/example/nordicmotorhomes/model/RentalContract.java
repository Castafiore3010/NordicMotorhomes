package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class RentalContract {
    @Id
    private int rental_contract_id;
    private LocalDateTime start_datetime;
    private LocalDateTime end_datetime;
    private int person_id; // customer objekt?
    private int motorhome_id; // objekt?
    private int contact_point_pickup_id; // objekt
    private int contact_point_dropoff_id; // objekt
    // private List<Extras> accessoryList; // liste af extra objekter.
}
