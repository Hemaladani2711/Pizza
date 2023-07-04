package com.example.pizza;

/**
 * The customizable interface.
 * @author Jit Patel
 * @author Tejas Shah
 */
public interface Customizable<E> {
    /**
     * Add a topping
     * @param obj
     * @return boolean
     */
    boolean add(Object obj);

    /**
     * Remove a topping
     * @param obj
     * @return boolean
     */
    boolean remove(Object obj);
}
