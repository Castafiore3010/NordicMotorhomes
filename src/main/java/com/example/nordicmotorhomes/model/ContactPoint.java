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


    // return distance between this location and that location
    // measured in kilometers
    public double distanceTo(ContactPoint that) {
        final double KILOMETERS_PER_NAUTICAL_MILE = 1.85200;
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(that.latitude);
        double lon2 = Math.toRadians(that.longitude);

        // great circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        double kilometers = KILOMETERS_PER_NAUTICAL_MILE * nauticalMiles;
        return kilometers;
    }
}
