package com.example.nordicmotorhomes.model;

import org.hibernate.sql.Insert;

import java.time.LocalDateTime;

public class InsertRentalContract {
    private LocalDateTime start_datetime;
    private LocalDateTime end_datetime;
    private int person_id;
    private int motorhome_id;
    private int pickup_id;
    private int dropoff_id;


    public InsertRentalContract() {}

    public InsertRentalContract(LocalDateTime start_datetime, LocalDateTime end_datetime, int person_id, int motorhome_id, int pickup_id, int dropoff_id) {
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.person_id = person_id;
        this.motorhome_id = motorhome_id;
        this.pickup_id = pickup_id;
        this.dropoff_id = dropoff_id;
    }
    public LocalDateTime getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(LocalDateTime start_datetime) {
        this.start_datetime = start_datetime;
    }

    public LocalDateTime getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(LocalDateTime end_datetime) {
        this.end_datetime = end_datetime;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getMotorhome_id() {
        return motorhome_id;
    }

    public void setMotorhome_id(int motorhome_id) {
        this.motorhome_id = motorhome_id;
    }

    public int getPickup_id() {
        return pickup_id;
    }

    public void setPickup_id(int pickup_id) {
        this.pickup_id = pickup_id;
    }

    public int getDropoff_id() {
        return dropoff_id;
    }

    public void setDropoff_id(int dropoff_id) {
        this.dropoff_id = dropoff_id;
    }
}
