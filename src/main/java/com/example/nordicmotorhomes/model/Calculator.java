package com.example.nordicmotorhomes.model;

import java.time.*;
import java.util.List;

public class Calculator {
    private final RentalContract rentalContract;
    private final ContactPoint pickUp;
    private final ContactPoint dropOff;
    private final List<ContactPoint> validPoints;
    private final double MOTORHOME_PRICE_PER_DAY;



    public Calculator(RentalContract rentalContract, Price price, ContactPoint pickUp, ContactPoint dropOff, List<ContactPoint> validPoints) {
        this.rentalContract = rentalContract;
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        this.validPoints = validPoints;

        LocalDate contractStartDate = rentalContract.getStart_datetime().toLocalDate();
        LocalDate summerEnd = LocalDate.of(contractStartDate.getYear(), Month.of(8), 31);
        LocalDate winterStart1 = LocalDate.of(contractStartDate.getYear(), Month.of(1), 1);
        LocalDate winterStart2 = LocalDate.of(contractStartDate.getYear(), Month.of(12), 1);
        LocalDate springStart = LocalDate.of(contractStartDate.getYear(), Month.of(3), 1);
        LocalDate springEnd = LocalDate.of(contractStartDate.getYear(), Month.of(5), 31);
        LocalDate fallStart = LocalDate.of(contractStartDate.getYear(), Month.of(9), 1);


        String contractSeason;
        if (contractStartDate.isAfter(springEnd) && contractStartDate.isBefore(fallStart)) {
            contractSeason = "summer";
        } else if(contractStartDate.isAfter(summerEnd) && contractStartDate.isBefore(winterStart2)) {
            contractSeason ="fall";
        } else if(contractStartDate.isAfter(winterStart1) && contractStartDate.isBefore(springStart)) {
            contractSeason ="spring";
        } else {
            contractSeason ="winter";
        }
        double middleSeasonPriceModifier = 1.30;
        double peakSeasonPriceModifier = 1.60;
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





    public double calculateMotorhomePriceForPeriod() {
        Period contractPeriod = Period.between(rentalContract.getStart_datetime().toLocalDate(), rentalContract.getEnd_datetime().toLocalDate());

        return MOTORHOME_PRICE_PER_DAY * contractPeriod.getDays();

    }

    public double calculateTransferCost(double distance) {
        int roundedDistance = (int) distance;
        double transferCostPerKilometer = 0.70;
        return roundedDistance * transferCostPerKilometer;

    }

    public ContactPoint closestValidPoint(ContactPoint initialPoint) {
        int id = -1;
        double shortestDistance = initialPoint.distanceTo(validPoints.get(0));

        for (ContactPoint contactPoint : validPoints) {
            if (contactPoint.distanceTo(initialPoint) < shortestDistance) {
                shortestDistance = contactPoint.distanceTo(initialPoint);
                id = contactPoint.getContact_point_id();
            }
        }

        return validPoints.get(id);



    }

    public double calculateTotalContractPrice() {
        double motorhomeTotalPriceForPeriod = calculateMotorhomePriceForPeriod();

        double distanceToClosestPickUp = pickUp.distanceTo(closestValidPoint(pickUp));
        double distanceToClosestDropOff = dropOff.distanceTo(closestValidPoint(dropOff));

        double transferCostPickUp = calculateTransferCost(distanceToClosestPickUp);
        double transferCostDropOff = calculateTransferCost(distanceToClosestDropOff);

        return motorhomeTotalPriceForPeriod + transferCostPickUp + transferCostDropOff;

    }
}
