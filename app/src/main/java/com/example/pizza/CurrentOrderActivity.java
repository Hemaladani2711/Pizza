package com.example.pizza;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.text.DecimalFormat;
import java.util.ArrayList;

/** The current order class
 * @author Jit Patel
 * @author Tejas Shah
 */
public class CurrentOrderActivity extends AppCompatActivity {

    private Order order;
    private PizzaAdapter pizzaAdapter;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_order);
        setResult(MainActivity.NO_ACTION_YET);

        Intent intent = getIntent();
        int orderNumber = intent.getIntExtra("orderNumber", 0);
        ArrayList<Pizza> pizzas = (ArrayList<Pizza>) intent.getSerializableExtra("orders");

        order = new Order(orderNumber);
        order.setPizzas(pizzas);
        pizzaAdapter = new PizzaAdapter(pizzas);

        TextView orderTextView = findViewById(R.id.order_number);
        orderTextView.setText("" + orderNumber);
        setupPizzaList();
        setupDeletePizza();
        setupPlaceOrder();
        setupClearAllOrder();
        updatePrices();
    }

    /**
     * to clears all the orders
     */
    private void setupClearAllOrder() {
        Button clearAllOrders = findViewById(R.id.clearOrder);
        clearAllOrders.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.clear_entire_order);
            alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    pizzaAdapter.removeAll();
                    updatePrices();

                    Intent intent = new Intent();
                    intent.putExtra("orders", order.getPizzas());
                    setResult(MainActivity.CURRENT_ORDER_UPDATED, intent);
                    Toast.makeText(getApplicationContext(), R.string.cleared_entire_order, Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        });
    }

    /**
     * to set up the order
     */
    private void setupPlaceOrder() {
        Button placeOrder = findViewById(R.id.placeOrderButton);
        placeOrder.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("orders", order.getPizzas());
            setResult(MainActivity.CURRENT_ORDER, intent);
            finish();
        });
    }

    /**
     * to delete the selected pizza
     */
    private void setupDeletePizza() {
        Button deletePizza = findViewById(R.id.deletePizza);
        deletePizza.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.delete_selected_pizzas);
            alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    pizzaAdapter.removeSelectedPizzas();
                    updatePrices();

                    Intent intent = new Intent();
                    intent.putExtra("orders", order.getPizzas());
                    setResult(MainActivity.CURRENT_ORDER_UPDATED, intent);
                    Toast.makeText(getApplicationContext(), R.string.pizza_deleted, Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        });
    }

    /**
     * to make the pizza list
     */
    private void setupPizzaList() {
        Intent intent = getIntent();
        RecyclerView pizzaRecyclerView = findViewById(R.id.currentOrderRecyclerView);
        pizzaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pizzaRecyclerView.setAdapter(pizzaAdapter);
    }

    /**
     * ot update the prize when adding or removing pizza
     */
    private void updatePrices() {
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        Chip subtotalChip = findViewById(R.id.subtotalChip);
        Chip salesTaxChip = findViewById(R.id.salesTaxChip);
        Chip orderTotalChip = findViewById(R.id.orderTotalChip);

        subtotalChip.setText(formatter.format(order.getSubTotal()));
        salesTaxChip.setText(formatter.format(order.getSalesTax()));
        orderTotalChip.setText(formatter.format(order.getOrderTotal()));
    }
}

