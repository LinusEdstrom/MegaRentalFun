package com.Edstrom.pricing;

public class Standard implements PricePolicy {

    public Standard (){}

    @Override
    public double priceCalculator(double basePrice, int days){
        return basePrice * days;
    }
}
