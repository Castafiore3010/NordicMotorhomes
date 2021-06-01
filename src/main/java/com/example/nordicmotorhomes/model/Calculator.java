package com.example.nordicmotorhomes.model;

import org.apache.tomcat.jni.Local;

import java.time.*;
import java.util.List;
/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
public class Calculator {
    private final RentalContract rentalContract;
    private final ContactPoint pickUp;
    private final ContactPoint dropOff;
    private final List<ContactPoint> validPoints;
    private final double MOTORHOME_PRICE_PER_DAY;
    private String contract_season;


    public Calculator(RentalContract rentalContract, Price price, ContactPoint pickUp, ContactPoint dropOff, List<ContactPoint> validPoints) {
        this.rentalContract = rentalContract;
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        this.validPoints = validPoints;

        LocalDate contractStartDate = rentalContract.getStart_datetime().toLocalDate();
        LocalDate summerEnd = LocalDate.of(contractStartDate.getYear(), Month.of(8), 31);
        LocalDate summerStart = LocalDate.of(contractStartDate.getYear(), Month.of(6), 1);
        LocalDate winterStart1 = LocalDate.of(contractStartDate.getYear(), Month.of(1), 1);
        LocalDate winterStart2 = LocalDate.of(contractStartDate.getYear(), Month.of(12), 1);
        LocalDate winterEnd1 = LocalDate.of(contractStartDate.getYear(), Month.of(2), Year.isLeap(contractStartDate.getYear()) ? 29 : 28);
        LocalDate springStart = LocalDate.of(contractStartDate.getYear(), Month.of(3), 1);
        LocalDate springEnd = LocalDate.of(contractStartDate.getYear(), Month.of(5), 31);
        LocalDate fallStart = LocalDate.of(contractStartDate.getYear(), Month.of(9), 1);
        LocalDate fallEnd = LocalDate.of(contractStartDate.getYear(), Month.of(11), 30);




        String contractSeason="";

        if (contractStartDate.isAfter(springEnd) && contractStartDate.isBefore(fallStart)) {
            contractSeason = "summer";
        }
        else if(contractStartDate.isAfter(summerEnd) && contractStartDate.isBefore(winterStart2)) {
            contractSeason ="fall";
        }
        else if(contractStartDate.isAfter(winterEnd1) && contractStartDate.isBefore(summerStart)) {
            contractSeason ="spring";
        }
        else if (contractStartDate.isAfter(fallEnd) && contractStartDate.isBefore(winterStart1)) {
            contractSeason = "winter";
        }
        else if (contractStartDate.isBefore(winterEnd1) && (contractStartDate.isAfter(winterStart1) || contractStartDate.isEqual(winterStart1))) {
            contractSeason ="winter";
        }
        this.contract_season = contractSeason;
        double middleSeasonPriceModifier = 1.30; // spring, fall modifier.
        double peakSeasonPriceModifier = 1.60; // summer modfier
        switch (contractSeason) {
            case "summer":
                MOTORHOME_PRICE_PER_DAY = price.getPrice_per_day() * peakSeasonPriceModifier;
                break;
            case "spring": case "fall":
                MOTORHOME_PRICE_PER_DAY = price.getPrice_per_day() * middleSeasonPriceModifier;
                break;
            default:
                MOTORHOME_PRICE_PER_DAY = price.getPrice_per_day();
        }
    }


    public String getContract_season() {
        return contract_season;
    }

    public void setContract_season(String contract_season) {
        this.contract_season = contract_season;
    }


    /**
     * @author Marc
     * @return a double representing the price for the motorhome in a period
     */
    public double calculateMotorhomePriceForPeriod() {
        Duration duration = Duration.between(rentalContract.getStart_datetime(), rentalContract.getEnd_datetime());
        return  MOTORHOME_PRICE_PER_DAY * duration.toDays();
    }

    /**
     * @author Michael
     * @param distance
     * @return the rounded amount
     */
    public double calculateTransferCost(double distance) {
        int roundedDistance = (int) distance;
        double PRICE_PER_KM_FROM_CONTACT_POINT = 0.7;
        return roundedDistance * PRICE_PER_KM_FROM_CONTACT_POINT;

    }

    /**
     * @author Marc, Samavia
     * @param initialPoint ContactPoint object, to be checked to a List of other ContantPoints
     * @return the closest ContactPoint object compared to the given initialPoint
     */
    public ContactPoint closestValidPoint(ContactPoint initialPoint) {
        System.out.println(contract_season);
        int id = -1; // id - updated to correct value line 109
        double shortestDistance = 10000; // value larger than largest possible
        System.out.println("INITIAL POINT : " + initialPoint.getContact_point_name());


        for (ContactPoint contactPoint : validPoints) { // for each
            System.out.println(contactPoint.getContact_point_name());
            System.out.println(shortestDistance);
            // if distance between current element in list and initialPoint is shorter than shortestdistance :
            if (contactPoint.distanceTo(initialPoint) <= shortestDistance) {
                shortestDistance = contactPoint.distanceTo(initialPoint); // update distance value
                System.out.println(shortestDistance);
                id = contactPoint.getContact_point_id(); // update id
                System.out.println(id);
            }
        }

        System.out.println("CLOSEST ID: " + id);
        return validPoints.get(id -1); // return "found" element in list.

    }


    /**
     * @author Emma
     * @return The total price for the contract
     */
    public double calculateTotalContractPrice() {
        double motorhomeTotalPriceForPeriod = (calculateMotorhomePriceForPeriod()); //the price found from the period alone


        double distanceToClosestPickUp = pickUp.distanceTo(closestValidPoint(pickUp)); //The distance closest to the pickup-point chosen
        double distanceToClosestDropOff = dropOff.distanceTo(closestValidPoint(dropOff)); //The distance closest to the dropoff-point chosen


        double transferCostPickUp = calculateTransferCost(distanceToClosestPickUp); //add the distance to the specified 0.7€ for each kilometer
        double transferCostDropOff = calculateTransferCost(distanceToClosestDropOff); //add the distance to the specified 0.7€ for each kilometer

        return motorhomeTotalPriceForPeriod + transferCostPickUp + transferCostDropOff;

    }
}
