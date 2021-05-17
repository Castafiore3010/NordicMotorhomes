package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Motorhome {
    @Id
    private int motorhome_id;
    private String model_name;
    private String brand_name;
    private int capacity;
    private double odometer;


    public Motorhome() {}


    public Motorhome(int motorhome_id, String model_name, String brand_name, int capacity, double odometer, int price_id) {
        this.motorhome_id = motorhome_id;
        this.model_name = model_name;
        this.brand_name = brand_name;
        this.capacity = capacity;
        this.odometer = odometer;
        this.price_id = price_id;
    }

    private int price_id; // evt price objekt?


    //<editor-fold desc="Getters and setters">
    public int getMotorhome_id() {
        return motorhome_id;
    }

    public void setMotorhome_id(int motorhome_id) {
        this.motorhome_id = motorhome_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }
    //</editor-fold>

}
