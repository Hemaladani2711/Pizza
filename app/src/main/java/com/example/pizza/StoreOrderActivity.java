package com.example.pizza;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/** Store activity
 *  * @author Jit Patel
 *  * @author Tejas Shah
 */
public class StoreOrderActivity extends AppCompatActivity {
    private StoreOrders storeOrders = new StoreOrders();
    private ArrayAdapter<String> adapter;
    private static final int PICK_FILE = 1;
    ActivityResultLauncher<String> fileLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_orders);
        setResult(MainActivity.NO_ACTION_YET);

        Intent intent = getIntent();
        storeOrders.setOrders((ArrayList<Order>) intent.getSerializableExtra("orders"));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        setupOrderNumbers();
        updateOrdersList();
        setupListeners();

        fileLauncher = registerForActivityResult(new ActivityResultContracts.CreateDocument(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        try {
                            storeOrders.export(getContentResolver().openOutputStream(result));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setupListeners() {
        Spinner orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        orderNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateOrdersList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button cancelOrder = findViewById(R.id.cancelOrderButton);
        cancelOrder.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.cancel_order);
            alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    cancelSelectedOrder();
                    Toast.makeText(getApplicationContext(), R.string.order_cancelled, Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {}
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        });

        Button exportButton = findViewById(R.id.export_button);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileLauncher.launch(".txt");
            }
        });
    }

    private void cancelSelectedOrder() {
        Spinner orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        RecyclerView storeOrdersRecyclerView = findViewById(R.id.storeOrderRecyclerView);
        if (orderNumberSpinner.getSelectedItem() == null) {
            return;
        }

        Intent intent = new Intent();
        int orderNum = Integer.parseInt(orderNumberSpinner.getSelectedItem().toString());
        storeOrders.remove(orderNum);
        intent.putExtra("orders", storeOrders.getStoreOrders());
        setResult(MainActivity.STORE_ORDER_UPDATED, intent);

        adapter.remove(orderNum + "");
        adapter.notifyDataSetChanged();
        orderNumberSpinner.setAdapter(adapter);

        if (adapter.getCount() == 0) {
            Chip priceChip = findViewById(R.id.storeOrderTotalChip);
            storeOrdersRecyclerView.setAdapter(new PizzaAdapter(new ArrayList<>()));
            priceChip.setText(R.string.dollar_sign);
        } else {
            updateOrdersList();
        }
    }

    private void updatePrice() {
        Spinner orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        if (orderNumberSpinner == null || orderNumberSpinner.getSelectedItem() == null) {
            return;
        }

        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        Chip storeOrderChip = findViewById(R.id.storeOrderTotalChip);
        int orderNum = Integer.parseInt(orderNumberSpinner.getSelectedItem().toString());
        Order order = find(orderNum);
        if (order != null) {
            String total = formatter.format(order.getOrderTotal());
            storeOrderChip.setText(total);
        }
    }

    private void setupOrderNumbers() {
        Spinner orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        for (Order order: storeOrders.getStoreOrders()) {
            if (order != null) {
                adapter.add("" + order.getOrderNumber());
            }
        }

        orderNumberSpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void updateOrdersList() {
        RecyclerView storeOrders = findViewById(R.id.storeOrderRecyclerView);
        Spinner orderNumberSpinner = findViewById(R.id.orderNumberSpinner);
        if (orderNumberSpinner.getSelectedItem() == null) {
            return;
        }

        int orderNum = Integer.parseInt(orderNumberSpinner.getSelectedItem().toString());
        Order order = find(orderNum);
        if (order == null) {
            return;
        }

        ArrayList<Pizza> pizzas = order.getPizzas();
        PizzaAdapter pizzaAdapter = new PizzaAdapter(pizzas, true);
        storeOrders.setLayoutManager(new LinearLayoutManager(this));
        storeOrders.setAdapter(pizzaAdapter);
        updatePrice();
    }

    private Order find(int orderNum) {
        for (Order order: storeOrders.getStoreOrders()) {
            if (order != null && order.getOrderNumber() == orderNum) {
                return order;
            }
        }

        return null;
    }




}

