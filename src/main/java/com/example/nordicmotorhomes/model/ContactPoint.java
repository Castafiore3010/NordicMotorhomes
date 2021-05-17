package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ContactPoint {
    @Id
    private int contact_point_id;
    private double latitude, longitude;
    private String contact_point_name;
    private boolean validLocation;



    public ContactPoint() {}



    public ContactPoint(int contactPoint_id, double latitude, double longitude, String contact_point_name, boolean validLocation) {
        this.contact_point_id = contactPoint_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contact_point_name = contact_point_name;
        this.validLocation = validLocation;
    }


    public int getContact_point_id() {
        return contact_point_id;
    }

    public void setContact_point_id(int contactPoint_id) {
        this.contact_point_id = contactPoint_id;
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

    public String getContact_point_name() {
        return contact_point_name;
    }

    public void setContact_point_name(String contactPoint_name) {
        this.contact_point_name = contactPoint_name;
    }

    public boolean isValidLocation() {
        return validLocation;
    }

    public void setValidLocation(boolean validLocation) {
        this.validLocation = validLocation;
    }
}
