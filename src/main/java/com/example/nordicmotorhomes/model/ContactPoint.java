package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ContactPoint {
    @Id
    private int contact_point_id;
    private double latitude, longitude;
    private String contact_point_name;
    private String contact_point_type;
    private int address_id;
    private String street_name;
    private String zipcode;
    private String city_name;



    public ContactPoint() {}

    public ContactPoint(double latitude, double longitude, String street_name, String zipcode, String city_name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street_name = street_name;
        this.zipcode = zipcode;
        this.city_name = city_name;
    }



    public ContactPoint(int contactPoint_id, double latitude, double longitude,
                        String contact_point_name, String contact_point_type, int address_id,
                        String street_name, String zipcode, String city_name) {
        this.contact_point_id = contactPoint_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contact_point_name = contact_point_name;
        this.contact_point_type = contact_point_type;
        this.address_id = address_id;
        this.street_name=street_name;
        this.zipcode=zipcode;
        this.city_name=city_name;
    }

    public ContactPoint(int contact_point_id, String contact_point_name, String contact_point_type, String street_name, String zipcode, String city_name, int address_id) {
        this.contact_point_id = contact_point_id;
        this.contact_point_name = contact_point_name;
        this.contact_point_type = contact_point_type;
        this.street_name = street_name;
        this.zipcode = zipcode;
        this.city_name = city_name;
        this.address_id = address_id;
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

    public String getContact_point_type() {
        return contact_point_type;
    }

    public void setContact_point_type(String validLocation) {
        this.contact_point_type = validLocation;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
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
