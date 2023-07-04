package com.example.pizza;

/**
 * The NY Pizza type class.
 * @author Jit Patel
 * @author Tejas Shah
 */

public class NYPizza implements PizzaFactory {
    /**
     * Sets the new pizza to Deluxe
     * @return Deluxe Pizza
     */
    @Override
    public Pizza createDeluxe() {
        Pizza pizza = new Deluxe();
        pizza.setFlavor(Flavor.Deluxe);
        return pizza;
    }

    /**
     * Sets the new pizza to Meatzza
     * @return Meatzza pizza
     */
    @Override
    public Pizza createMeatzza() {
        Pizza pizza = new Meatzza();
        pizza.setFlavor(Flavor.Meatzza);
        return pizza;
    }

    /**
     * Sets the new pizza to BBQChicken
     * @return BBQChicken Pizza
     */
    @Override
    public Pizza createBBQChicken() {
        Pizza pizza = new BBQChicken();
        pizza.setFlavor(Flavor.BBQChicken);
        return pizza;
    }

    /**
     * Sets the new pizza to BuildYourOwn
     * @return BuildYourOwn Pizza
     */
    @Override
    public Pizza createBuildYourOwn() {
        Pizza pizza = new BuildYourOwn();
        pizza.setFlavor(Flavor.BuildYourOwn);
        return pizza;
    }
}
