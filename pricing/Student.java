package com.Edstrom.pricing;

public class Student implements PricePolicy {

    public Student (){}

    @Override
    public double priceCalculator(double basePrice, int days){
        return basePrice * days * 0.8;
    }
}
