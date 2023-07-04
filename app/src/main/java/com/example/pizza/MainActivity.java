package com.example.pizza;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/** The main activity class
 *  * @author Jit Patel
 *  * @author Tejas Shah
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView orderChicagoStyle = findViewById(R.id.chicagoStyleImage);
        ImageView orderNYStyle = findViewById(R.id.nyStyleImage);
        ImageView currentOrder = findViewById(R.id.currentOrderImage);
        ImageView storeOrders = findViewById(R.id.storeOrdersImage);

        orderChicagoStyle.setOnClickListener(openActivity(OrderChicagoStyleActivity.class, PIZZA_ORDER));
        orderNYStyle.setOnClickListener(openActivity(OrderNYStyleActivity.class, PIZZA_ORDER));
        currentOrder.setOnClickListener(openActivity(CurrentOrderActivity.class, CURRENT_ORDER));
        storeOrders.setOnClickListener(openActivity(StoreOrderActivity.class, STORE_ORDER));
    }

    public static final int PIZZA_ORDER = 5;
    public static final int CURRENT_ORDER = 6;
    public static final int CURRENT_ORDER_UPDATED = 7;
    public static final int STORE_ORDER = 8;
    public static final int STORE_ORDER_UPDATED = 9;
    public static final int NO_ACTION_YET = 100;

    private View.OnClickListener openActivity(Class<?> activityClass, int requestCode) {
        MainActivity context = this;
        return (v -> {
            Intent intent = new Intent(context, activityClass);
            if (requestCode == CURRENT_ORDER) {
                intent.putExtra("orderNumber", currentOrderNum);
                intent.putExtra("orders", currentOrder.getPizzas());
            } else if (requestCode == STORE_ORDER) {
                intent.putExtra("orders", storeOrders.getStoreOrders());
            }

            startActivityForResult(intent, requestCode);
        });
    }

    int currentOrderNum = 0;
    private Order currentOrder = new Order(currentOrderNum);
    private StoreOrders storeOrders = new StoreOrders();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PIZZA_ORDER) {
            currentOrder.add((Pizza) data.getSerializableExtra("pizza"));
        } else if (resultCode == CURRENT_ORDER_UPDATED) {
            currentOrder.setPizzas((ArrayList<Pizza>) data.getSerializableExtra("orders"));
        } else if (resultCode == CURRENT_ORDER) {
            currentOrder.setPizzas((ArrayList<Pizza>) data.getSerializableExtra("orders"));
            storeOrders.add(currentOrder);
            currentOrderNum += 1;
            currentOrder = new Order(currentOrderNum);
        } else if (resultCode == STORE_ORDER_UPDATED) {
            storeOrders.setOrders((ArrayList<Order>) data.getSerializableExtra("orders"));
        }
    }
}

