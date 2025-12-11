package com.Edstrom.pricing;

public interface PricePolicy {

    double priceCalculator(double basePrice, int days);
}
