package com.example.pizza;

/**
 * The meatzza pizza class
 * @author Jit Patel
 * @author Tejas Shah
 */
public class Meatzza extends Pizza {
    private final double SMALL_PRICE = 15.99;
    private final double MEDIUM_PRICE = 17.99;
    private final double LARGE_PRICE = 19.99;

    /**
     * to find the price of the pizza based on the size
     * @return prize
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
