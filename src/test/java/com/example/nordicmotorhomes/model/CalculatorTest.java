package com.example.nordicmotorhomes.model;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void calculateMotorhomePriceForPeriod() {
        LocalDateTime ldt1 = LocalDateTime.of(2020,01,01,15,00);
        LocalDateTime ldt2 = LocalDateTime.of(2020,01,03,15,00);
        LocalDateTime ldt3 = LocalDateTime.of(2020,12,31,15,00);
        LocalDateTime ldt4 = LocalDateTime.of(2021,01,07,15,00);
        LocalDateTime ldt5 = LocalDateTime.of(2020,03,01,15,00);
        LocalDateTime ldt6 = LocalDateTime.of(2020,03,05,15,00);
        LocalDateTime ldt7 = LocalDateTime.of(2020,06,01,15,00);
        LocalDateTime ldt8 = LocalDateTime.of(2020,06,05,15,00);
        RentalContract rentalContract = new RentalContract(ldt1,ldt2,1,1,1,2);
        Price price = new Price(1,100);
        ContactPoint cp1 = new ContactPoint(55.40389,10.38568,"Østerstationsvej 27","5000","Odense");
        ContactPoint cp2 = new ContactPoint(55.74094,9.15263,"Passagerterminalen 10","7190","Billund");
        ContactPoint cp3 = new ContactPoint(55.8406,55.8406,"Stationsvej 5","3460","Birkerød");
        ContactPoint cp4 = new ContactPoint(55.57663,9.77532,"Idasvej 5","7000","Fredericia");
        List<ContactPoint> listCp = new ArrayList<>();
        listCp.add(cp1); listCp.add(cp2);
        Calculator calculator = new Calculator(rentalContract,price,cp1,cp2,listCp);

        RentalContract rentalContract2 = new RentalContract(ldt3,ldt4,1,1,1,2);
        Calculator calculator2 = new Calculator(rentalContract2,price,cp3,cp4,listCp);

        RentalContract rentalContract3 = new RentalContract(ldt5,ldt6,1,1,1,2);
        Calculator calculatorSpring = new Calculator(rentalContract3,price,cp3,cp4,listCp);

        RentalContract rentalContract4 = new RentalContract(ldt7,ldt8,1,1,1,2);
        Calculator calculatorSummer = new Calculator(rentalContract4,price,cp3,cp4,listCp);

        double actual = calculator.calculateMotorhomePriceForPeriod();
        double expected = 200;
        assertEquals(actual,expected); // tester vinterperiode

        double actual2 = calculator2.calculateMotorhomePriceForPeriod();
        double expected2 = 700;
        assertEquals(actual2,expected2); //tester årsskiftte

        double actual3 = calculatorSpring.calculateMotorhomePriceForPeriod();
        double expected3 = 400*1.3; //testing spring season price
        assertEquals(actual3,expected3);

        double actualSummer = calculatorSummer.calculateMotorhomePriceForPeriod();
        double expectedSummer = 400*1.6; //testing summer price
        assertEquals(actualSummer,expectedSummer);
    }

    @Test
    void calculateTransferCost() {
        LocalDateTime ldt1 = LocalDateTime.of(2020,01,01,15,00);
        LocalDateTime ldt2 = LocalDateTime.of(2020,01,03,15,00);
        RentalContract rentalContract = new RentalContract(ldt1,ldt2,1,1,1,2);
        Price price = new Price(1,100);
        ContactPoint cp1 = new ContactPoint(55.40389,10.38568,"Østerstationsvej 27","5000","Odense");
        ContactPoint cp2 = new ContactPoint(55.74094,9.15263,"Passagerterminalen 10","7190","Billund");
        ContactPoint cp3 = new ContactPoint(55.8406,55.8406,"Stationsvej 5","3460","Birkerød");
        ContactPoint cp4 = new ContactPoint(55.57663,9.77532,"Idasvej 5","7000","Fredericia");
        List<ContactPoint> listCp = new ArrayList<>();
        listCp.add(cp1); listCp.add(cp2);
        Calculator calculator = new Calculator(rentalContract,price,cp1,cp2,listCp);

        double actual = calculator.calculateTransferCost(350.5);
        double expected =350*0.7;
        assertEquals(actual,expected);
    }

    @Test
    void closestValidPoint() {
        LocalDateTime ldt1 = LocalDateTime.of(2020,01,01,15,00);
        LocalDateTime ldt2 = LocalDateTime.of(2020,01,03,15,00);
        RentalContract rentalContract = new RentalContract(ldt1,ldt2,1,1,1,2);
        Price price = new Price(1,100);
        ContactPoint cp1 = new ContactPoint(1,55.40389,10.38568,"Østerstationsvej 27","5000","Odense");
        ContactPoint cp2 = new ContactPoint(2,55.74094,9.15263,"Passagerterminalen 10","7190","Billund");
        ContactPoint cp3 = new ContactPoint(3,55.8406,55.8406,"Stationsvej 5","3460","Birkerød");
        ContactPoint cp4 = new ContactPoint(4,55.57663,9.77532,"Idasvej 5","7000","Fredericia");
        ContactPoint cp5 = new ContactPoint(5,57.73479,10.60427,"Bøjlevejen 2","9990","Skagen");

        List<ContactPoint> listCp = new ArrayList<>();
        listCp.add(cp1); listCp.add(cp2); listCp.add(cp4);

        Calculator calculator = new Calculator(rentalContract,price,cp1,cp2,listCp);


        ContactPoint actual = calculator.closestValidPoint(cp3);
        ContactPoint expected = cp1;
        assertEquals(actual,expected);

        ContactPoint actual2 = calculator.closestValidPoint(cp5);
        ContactPoint expected2 = cp2; //tester ikke predefineret point
        assertEquals(actual2,expected2);
    }

    @Test
    void calculateTotalContractPrice() {
        LocalDateTime ldt1 = LocalDateTime.of(2020,1,1,15,0);
        LocalDateTime ldt2 = LocalDateTime.of(2020,1,3,15,0);
        LocalDateTime ldt3 = LocalDateTime.of(2020,9,1,15, 0);
        LocalDateTime ldt4 = LocalDateTime.of(2020,9,11,15,0);
        LocalDateTime ldt5 = LocalDateTime.of(2020,6,1,15, 0);
        LocalDateTime ldt6 = LocalDateTime.of(2020,6,11,15,0);
        RentalContract rentalContract = new RentalContract(ldt1,ldt2,1,1,1,2);
        RentalContract rentalContract2 = new RentalContract(ldt3,ldt4,1,1,1,2);
        RentalContract rentalContract3 = new RentalContract(ldt5,ldt6,1,1,1,5);
        Price price = new Price(1,100);
        ContactPoint cp1 = new ContactPoint(1,55.40389,10.38568,"Østerstationsvej 27","5000","Odense");
        ContactPoint cp2 = new ContactPoint(2,55.74094,9.15263,"Passagerterminalen 10","7190","Billund");
        ContactPoint cp3 = new ContactPoint(3,55.8406,55.8406,"Stationsvej 5","3460","Birkerød");
        ContactPoint cp4 = new ContactPoint(4,55.57663,9.77532,"Idasvej 5","7000","Fredericia");
        ContactPoint cp5 = new ContactPoint(5,57.73479,10.60427,"Bøjlevejen 2","9990","Skagen");

        List<ContactPoint> listCp = new ArrayList<>();
        listCp.add(cp1); listCp.add(cp2);
        Calculator calculator = new Calculator(rentalContract,price,cp1,cp2,listCp);
        Calculator calculatorFall = new Calculator(rentalContract2,price,cp1,cp2,listCp);
        Calculator calculatorSummer = new Calculator(rentalContract3,price,cp1,cp5,listCp);

        double actual = calculator.calculateTotalContractPrice();
        double expected = 200;
        assertEquals(actual,expected); // tester to valide punkter uden prismodifikation.

        //tester efterår, 1.3 pris modifikation, 2 valid points
        double actualFall = calculatorFall.calculateTotalContractPrice();
        double expectedFall = 100*10*1.3;
        assertEquals(actualFall,expectedFall);

        //tester sommer, ét invalid point.
        double actualSummer = calculatorSummer.calculateTotalContractPrice();
        double expectedSummer = 100*10*1.6+(238*0.7); //bilpris*længde*sommerpris+afstandTilNærmestePointPris
        assertEquals(actualSummer,expectedSummer);

    }
}