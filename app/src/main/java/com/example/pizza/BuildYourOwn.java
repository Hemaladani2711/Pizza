package com.example.pizza;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/** The Build Your Own pizza class
 * @author Jit Patel
 * @author Tejas Shah
 */
public class BuildYourOwn extends Pizza {
    private final double SMALL_PRICE = 8.99;
    private final double MEDIUM_PRICE = 10.99;
    private final double LARGE_PRICE = 12.99;
    private final double TOPPINGS_COST = 1.59;

    /**
     * to find the price of the pizza based on the size
     *
     * @return price
     */
    @Override
    public double price() {
        double price = LARGE_PRICE;
        if (this.getSize() == Size.Small) {
            price = SMALL_PRICE;
        }

        if (this.getSize() == Size.Medium) {
            price = MEDIUM_PRICE;
        }

        price += this.getToppings().size() * TOPPINGS_COST;

        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.UP);
        return Double.valueOf(nf.format(price));
    }
}
