package com.example.pizza;

/**
 * The deluxe pizza class
 * @author Jit Patel
 * @author Tejas Shah
 */
public class Deluxe extends Pizza {
    private final double SMALL_PRICE = 14.99;
    private final double MEDIUM_PRICE = 16.99;
    private final double LARGE_PRICE = 18.99;

    /**
     * to find the price of the pizza based on the size
     * @return price
     */
    @Override
    public double price() {
        if (this.getSize() == Size.Small) {
            return SMALL_PRICE;
        }

        if (this.getSize() == Size.Medium) {
            return MEDIUM_PRICE;
        }

        return LARGE_PRICE;
    }
}
