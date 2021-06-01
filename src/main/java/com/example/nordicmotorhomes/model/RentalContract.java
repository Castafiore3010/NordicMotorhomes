package com.example.nordicmotorhomes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
@Entity
public class RentalContract {
    private int motorhome_id;
    private int person_id;
    @Id
    private int rental_contract_id;
    private LocalDateTime start_datetime;
    private LocalDateTime end_datetime;
    private int contact_point_pickup_id;
    private int contact_point_dropoff_id;
    private String pickup_name;
    private String pickup_type;
    private String dropoff_name;
    private String dropoff_type;
    private String first_name;
    private String last_name;
    private String email;
    private String phonenumber;
    private String model_name;
    private String brand_name;
    private int capacity;
    private int price_id;
    // private List<Extras> accessoryList; // liste af extra objekter.

    public RentalContract() {}

    public RentalContract(LocalDateTime start_datetime, LocalDateTime end_datetime, int person_id, int motorhome_id, int contact_point_pickup_id, int contact_point_dropoff_id) {
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.person_id = person_id;
        this.motorhome_id = motorhome_id;
        this.contact_point_pickup_id = contact_point_pickup_id;
        this.contact_point_dropoff_id = contact_point_dropoff_id;
    }


    public RentalContract(int motorhome_id, int person_id, int rental_contract_id, LocalDateTime start_datetime,
                          LocalDateTime end_datetime, int contact_point_pickup_id, int contact_point_dropoff_id,
                          String pickup_name, String pickup_type, String dropoff_name, String dropoff_type,
                          String first_name, String last_name, String email, String phonenumber, String model_name,
                          String brand_name, int capacity, int price_id) {

        this.motorhome_id = motorhome_id;
        this.person_id = person_id;
        this.rental_contract_id = rental_contract_id;
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.contact_point_pickup_id = contact_point_pickup_id;
        this.contact_point_dropoff_id = contact_point_dropoff_id;
        this.pickup_name = pickup_name;
        this.pickup_type = pickup_type;
        this.dropoff_name = dropoff_name;
        this.dropoff_type = dropoff_type;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.model_name = model_name;
        this.brand_name = brand_name;
        this.capacity = capacity;
        this.price_id = price_id;
    }



    //<editor-fold desc="String Help Methods">
    public String toDateStringEnd() {
        String dateString;
        if (this.end_datetime.getMonthValue() < 10 && this.end_datetime.getDayOfMonth() < 10) {
            dateString = "" + this.end_datetime.getYear() + "/" + "0" + this.end_datetime.getMonthValue() + "/" + "0" + this.end_datetime.getDayOfMonth();
        } else if (this.start_datetime.getMonthValue() < 10) {
            dateString = "" + this.end_datetime.getYear() + "/" + "0" + this.end_datetime.getMonthValue() + "/" + this.end_datetime.getDayOfMonth();
        } else if (this.end_datetime.getDayOfMonth() < 10) {
            dateString = "" + this.end_datetime.getYear() + "/" + this.end_datetime.getMonthValue() + "/" + "0" +  this.end_datetime.getDayOfMonth();
        } else {
            dateString = "ERROR";
        }
        return dateString;
    }

    public String toDateStringStart() {
        String dateString;
        if (this.start_datetime.getMonthValue() < 10 && this.start_datetime.getDayOfMonth() < 10) {
             dateString = "" + this.start_datetime.getYear() + "/" + "0" + this.start_datetime.getMonthValue() + "/" + "0" +this.start_datetime.getDayOfMonth();
        } else if (this.start_datetime.getMonthValue() < 10) {
            dateString = "" + this.start_datetime.getYear() + "/" + "0" + this.start_datetime.getMonthValue() + "/" + this.start_datetime.getDayOfMonth();
        } else if (this.start_datetime.getDayOfMonth() < 10) {
            dateString = "" + this.start_datetime.getYear() + "/" + this.start_datetime.getMonthValue() + "/" + "0" +  this.start_datetime.getDayOfMonth();
        } else {
            dateString = "ERROR";
        }
        return dateString;
    }
    //</editor-fold>

    //<editor-fold desc="Getters n Setters">
    public int getMotorhome_id() {
        return motorhome_id;
    }

    public void setMotorhome_id(int motorhome_id) {
        this.motorhome_id = motorhome_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getRental_contract_id() {
        return rental_contract_id;
    }

    public void setRental_contract_id(int rental_contract_id) {
        this.rental_contract_id = rental_contract_id;
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

    public int getContact_point_pickup_id() {
        return contact_point_pickup_id;
    }

    public void setContact_point_pickup_id(int contact_point_pickup_id) {
        this.contact_point_pickup_id = contact_point_pickup_id;
    }

    public int getContact_point_dropoff_id() {
        return contact_point_dropoff_id;
    }

    public void setContact_point_dropoff_id(int contact_point_dropoff_id) {
        this.contact_point_dropoff_id = contact_point_dropoff_id;
    }

    public String getPickup_name() {
        return pickup_name;
    }

    public void setPickup_name(String pickup_name) {
        this.pickup_name = pickup_name;
    }

    public String getPickup_type() {
        return pickup_type;
    }

    public void setPickup_type(String pickup_type) {
        this.pickup_type = pickup_type;
    }

    public String getDropoff_name() {
        return dropoff_name;
    }

    public void setDropoff_name(String dropoff_name) {
        this.dropoff_name = dropoff_name;
    }

    public String getDropoff_type() {
        return dropoff_type;
    }

    public void setDropoff_type(String dropoff_type) {
        this.dropoff_type = dropoff_type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }
    //</editor-fold>




}
