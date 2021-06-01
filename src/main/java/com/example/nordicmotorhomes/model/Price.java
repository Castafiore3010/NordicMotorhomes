package com.example.nordicmotorhomes.model;



import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
@Entity
public class Price {
    @Id
    private int price_id;
    private double price_per_day;


    public Price() {}


    public Price(int price_id, double price_per_day) {
        this.price_id = price_id;
        this.price_per_day = price_per_day;
    }

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }

    public double getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(double price_per_day) {
        this.price_per_day = price_per_day;
    }


}
