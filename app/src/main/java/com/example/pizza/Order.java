package com.example.pizza;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The order class
 * @author Jit Patel
 * @author Tejas Shah
 */
public class Order implements Serializable, Customizable {
    private int orderNumber;
    private ArrayList<Pizza> pizzas;

    /**
     * to create a new order
     * @param orderNumber
     */
    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
        this.pizzas = new ArrayList<>();
    }

    /**
     * to get the order number
     * @return order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * to get all the pizzas in the order
     * @return list of pizza
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * to set current pizza order
     * @param pizzas pizza list
     */
    public void setPizzas(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    /**
     * to remove all the pizzas from the current order
     */
    public void clearOrder() {
        this.pizzas = new ArrayList<>();
    }

    /**
     * t fet the order total
     * @return the order total
     */
    public double getOrderTotal() {
        return getSubTotal() + getSalesTax();
    }


    /**
     * to get order subtotal
     * @return subtotal
     */
    public double getSubTotal() {
        double subtotal = 0.0;
        for (Pizza pizza: pizzas) {
            subtotal += pizza.price();
        }

        return subtotal;
    }

    public static final double SALES_TAX_PERCENT = 6.625 / 100;

    /**
     * Created the method to generate the salesTax of the order
     * @return the salesTax of the order
     */
    public double getSalesTax() {
        return getSubTotal() * SALES_TAX_PERCENT;
    }

    /**
     * to add an order
     * @param obj
     * @return
     */
    @Override
    public boolean add(Object obj) {
        if (obj instanceof Pizza) {
            return pizzas.add((Pizza) obj);
        }

        return false;
    }

    /**
     * to remove an order
     * @param obj
     * @return
     */
    @Override
    public boolean remove(Object obj) {
        if (obj instanceof Pizza) {
            return pizzas.remove((Pizza) obj);
        }

        return false;
    }
}
