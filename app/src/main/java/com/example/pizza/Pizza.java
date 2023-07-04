package com.example.pizza;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The pizza class
 * @author Jit Patel
 * @author Tejas Shah
 */
public abstract class Pizza implements Customizable, Serializable {
    private ArrayList<Topping> toppings = new ArrayList<>();
    private Crust crust;
    private Size size;

    public final int BUILD_YOUR_OWN_TOPPINGS_LIMIT = 7;

    private Flavor flavor;

    /**
     * to find the price of the pizza based on the size and flavor
     * @return teh price of the pizza
     */
    public abstract double price();

    /**
     * to set up the flavors
     * @param flavor teh flavor that teh method was using at that moment
     */
    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    /**
     * to get the flavor
     * @return flavor
     */
    public Flavor getFlavor() {
        return flavor;
    }

    /**
     * to set up the crust
     * @param crust the crust type
     */
    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    /**
     *to get the crust
     * @return crust
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * @return list of the toppings
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }


    /**
     * to set the size of the pizza
     * @param size size of the pizza at that moment
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * to get the size of the pizza
     * @return the size of the pizza
     */
    public Size getSize() {
        return size;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return String.format(
                "%s %s, Price: $%s%s",
                size, this.getClass().getSimpleName(),
                formatter.format(this.price()), formatToppings(toppings)
        );
    }

    /**
     * to format the toppings into a readable string
     * @param toppings
     * @return toppings
     */
    private String formatToppings(ArrayList<Topping> toppings) {
        if (toppings.size() == 0) {
            return "";
        }

        String formattedToppings = toppings.toString();
        formattedToppings = formattedToppings.replace("[", "");
        formattedToppings = formattedToppings.replace("]", "");
        return ", Toppings: " + formattedToppings;
    }

    /**
     * to add the toppings on the BuildYourOwn Pizza
     * @param obj the toppings itself
     * @return the topping that was selected from the enum options
     */
    @Override
    public boolean add(Object obj) {
        if (obj instanceof Topping) {
            return this.getToppings().add((Topping) obj);
        }

        return false;
    }

    /**
     * to remove the toppings on the BuildYourOwn Pizza
     * @param obj the toppings itself
     * @return the toppings that was selected
     */
    @Override
    public boolean remove(Object obj) {
        if (obj instanceof Topping) {
            return this.getToppings().remove((Topping) obj);
        }

        return false;
    }
}
