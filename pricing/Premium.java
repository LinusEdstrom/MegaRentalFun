package com.Edstrom.pricing;

public class Premium implements PricePolicy {

    public Premium(){}

    @Override
    public double priceCalculator(double basePrice, int days){
        return basePrice * days * 0.6;
    }
}
