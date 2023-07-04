package com.example.pizza;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The store orders class
 * @author Jit Patel
 * @author Tejas Shah
 */
public class StoreOrders implements Customizable {

    private ArrayList<Order> storeOrders = new ArrayList<>();

    public ArrayList<Order> getStoreOrders() {
        return storeOrders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.storeOrders = orders;
    }

    /**
     * to remove the order
     * @param obj
     * @return boolean
     */
    @Override
    public boolean remove(Object obj) {
        if (obj instanceof Integer && (int) obj < storeOrders.size()) {
            storeOrders.set((int) obj, null);
            return true;
        }

        return false;
    }

    /**
     * to add the order
     * @param obj
     * @return boolean
     */
    @Override
    public boolean add(Object obj) {
        return storeOrders.add((Order) obj);
    }

    /**
     * to export the order to a file
     * @param outputStream
     * @throws IOException
     */
    public void export(OutputStream outputStream) throws IOException {
        StringBuilder sb = new StringBuilder();

        for (Order order: storeOrders) {
            if (order == null) {
                continue;
            }

            DecimalFormat formatter = new DecimalFormat("#,##0.00");
            sb.append("Order number: " + order.getOrderNumber() + "\n");
            sb.append("Price: $" + formatter.format(order.getOrderTotal()) + "\n");
            sb.append("Pizzas in order:" + "\n");
            for (Pizza pizza: order.getPizzas()) {
                sb.append("  - " + pizza + "\n");
            }

            sb.append("---------------------------------------------" + "\n");
            sb.append("" + "\n");
        }
        outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }


}
