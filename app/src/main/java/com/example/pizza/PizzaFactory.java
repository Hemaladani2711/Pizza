package com.example.pizza;

/**
 * The pizza factory class
 * @author Jit Patel
 * @author Tejas Shah
 */
public interface PizzaFactory {
    Pizza createDeluxe();
    Pizza createMeatzza();
    Pizza createBBQChicken();
    Pizza createBuildYourOwn();
}
