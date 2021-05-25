package com.example.nordicmotorhomes.model;

import org.apache.tomcat.jni.Local;

import java.time.*;

public class Calculator {
    private final double MIDDLE_SEASON_PRICE_MODIFIER = 1.30;
    private final double PEAK_SEASON_PRICE_MODIFIER = 1.60;
    private RentalContract rentalContract;
    private Price price;
    private final String SEASON;




    public Calculator(RentalContract rentalContract, Price price, String season) {
        this.rentalContract = rentalContract;
        this.price = price;

        LocalDate contractStartSeason = rentalContract.getStart_datetime().toLocalDate();
        LocalDate summerEnd = LocalDate.of(contractStartSeason.getYear(), Month.of(8), 31);
        LocalDate winterStart1 = LocalDate.of(contractStartSeason.getYear(), Month.of(1), 1);
        LocalDate winterStart2 = LocalDate.of(contractStartSeason.getYear(), Month.of(12), 1);
        LocalDate springStart = LocalDate.of(contractStartSeason.getYear(), Month.of(3), 1);
        LocalDate springEnd = LocalDate.of(contractStartSeason.getYear(), Month.of(5), 31);
        LocalDate fallStart = LocalDate.of(contractStartSeason.getYear(), Month.of(9), 1);


        if (contractStartSeason.isAfter(springEnd) && contractStartSeason.isBefore(fallStart)) {
            SEASON = "summer";
        } else if(contractStartSeason.isAfter(summerEnd) && contractStartSeason.isBefore(winterStart2)) {
            SEASON ="fall";
        } else if(contractStartSeason.isAfter(winterStart1) && contractStartSeason.isBefore(springStart)) {
            SEASON ="spring";
        } else {
            SEASON ="winter";
        }
    }





    public double calculatePriceForPeriod() {
        Period contractPeriod = Period.between(rentalContract.getStart_datetime().toLocalDate(), rentalContract.getEnd_datetime().toLocalDate());
        double pricePerDay;

        switch (SEASON) {
            case "summer":
                pricePerDay = price.getPrice_per_day() * PEAK_SEASON_PRICE_MODIFIER;
                break;
            case "spring": case "fall":
                pricePerDay = price.getPrice_per_day() * MIDDLE_SEASON_PRICE_MODIFIER;
            default:
                pricePerDay = price.getPrice_per_day();
        }

        return pricePerDay * contractPeriod.getDays();



    }
}
