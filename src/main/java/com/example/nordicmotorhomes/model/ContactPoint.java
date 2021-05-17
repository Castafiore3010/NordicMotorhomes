package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ContactPoint {
    @Id
    private int contactPoint_id;
    private double latitude, longitude;
    private String contactPoint_name;
    private boolean validLocation;



    public ContactPoint() {}



    public ContactPoint(int contactPoint_id, double latitude, double longitude, String contactPoint_name, boolean validLocation) {
        this.contactPoint_id = contactPoint_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactPoint_name = contactPoint_name;
        this.validLocation = validLocation;
    }


    public int getContactPoint_id() {
        return contactPoint_id;
    }

    public void setContactPoint_id(int contactPoint_id) {
        this.contactPoint_id = contactPoint_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getContactPoint_name() {
        return contactPoint_name;
    }

    public void setContactPoint_name(String contactPoint_name) {
        this.contactPoint_name = contactPoint_name;
    }

    public boolean isValidLocation() {
        return validLocation;
    }

    public void setValidLocation(boolean validLocation) {
        this.validLocation = validLocation;
    }
}
