package com.example.pizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;

/**
 * The activity for ordering ny style pizza.
 * @author Jit Patel
 * @author Tejas Shah
 */
public class OrderNYStyleActivity extends AppCompatActivity {
    /**
     * to create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_ny_style);

        setupPizzaFlavorSpinner();
        setupPizzaSizeSpinner();
        setupToppingsListView();
        updatePizza();

        initialize();
    }

    /**
     * to initialize the activity
     */
    private void initialize() {
        Spinner pizzaSize = findViewById(R.id.nyPizzaSize);
        Spinner pizzaFlavor = findViewById(R.id.nyPizzaFlavor);
        AdapterView.OnItemSelectedListener updatePizzaListener = new AdapterView.OnItemSelectedListener() {
            /**
             * Listener when the pizza flavor is changed.
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePizza();
            }

            /**
             * Listener is called when nothing is selected.
             * @param parent
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        pizzaSize.setOnItemSelectedListener(updatePizzaListener);
        pizzaFlavor.setOnItemSelectedListener(updatePizzaListener);

        Button orderButton = findViewById(R.id.nyOrderButton);
        orderButton.setOnClickListener(orderPizzaListener);
    }

    Pizza pizza = new NYPizza().createBuildYourOwn();

    /**
     * to update the current pizza
     */
    private void updatePizza() {
        Pizza currPizza = null;
        NYPizza nyPizza = new NYPizza();
        Spinner pizzaFlavor = findViewById(R.id.nyPizzaFlavor);

        String flavor = pizzaFlavor.getSelectedItem().toString();
        if (flavor.equals(Flavor.Deluxe.toString())) {
            currPizza = nyPizza.createDeluxe();
        } else if (flavor.equals(Flavor.Meatzza.toString())) {
            currPizza = nyPizza.createMeatzza();
        } else if (flavor.equals(Flavor.BBQChicken.toString())) {
            currPizza = nyPizza.createBBQChicken();
        } else {
            currPizza = nyPizza.createBuildYourOwn();
        }

        TextView crustTextView = findViewById(R.id.nyPizzaCrust);
        Spinner pizzaSize = findViewById(R.id.nyPizzaSize);

        String crust = crustTextView.getText().toString().split(":")[1];
        currPizza.setCrust(Crust.valueOf(crust.trim()));
        currPizza.setSize(Size.valueOf(pizzaSize.getSelectedItem().toString()));

        pizza = currPizza;
        updatePrice();
        updatePizzaCrust();
        updatePizzaImage();
        updateToppings();
    }


    /**
     * to update the toppings itself based on the pizza type
     */
    private void updateToppings() {
        Flavor selected = pizza.getFlavor();
        toppingsAdapter.clear();

        if (selected.equals(Flavor.BuildYourOwn)) {
            for (Topping topping: Topping.values()) {
                toppingsAdapter.add(topping);
            }
            toppingsListView.setEnabled(true);
            return;
        }

        if (selected.equals((Flavor.Deluxe))) {
            toppingsAdapter.addAll(
                    Topping.Sausage, Topping.Pepperoni, Topping.GreenPepper,
                    Topping.Onion, Topping.Mushroom
            );
        } else if (selected.equals(Flavor.Meatzza)) {
            toppingsAdapter.addAll(Topping.Sausage, Topping.Pepperoni, Topping.Beef, Topping.Ham);
        } else {
            toppingsAdapter.addAll(Topping.BBQChicken, Topping.GreenPepper, Topping.Provolone, Topping.Cheddar);
        }

        selectAllListViewItems(toppingsListView);
        toppingsListView.setEnabled(false);
    }

    /**
     * to update pizza image based on the selected flavor
     */
    private void updatePizzaImage() {
        ImageView pizzaImage = findViewById(R.id.nyPizzaImage);
        if (pizza instanceof BuildYourOwn) {
            pizzaImage.setImageResource(R.drawable.ny_byo);
        } else if (pizza instanceof Meatzza) {
            pizzaImage.setImageResource(R.drawable.ny_meatzza);
        } else if (pizza instanceof BBQChicken) {
            pizzaImage.setImageResource(R.drawable.ny_bbqchicken);
        } else {
            pizzaImage.setImageResource(R.drawable.ny_deluxe);
        }
    }

    /**
     * to update pizza crust based on the selected flavor
     */
    private void updatePizzaCrust() {
        Flavor selected = pizza.getFlavor();
        TextView crustType = findViewById(R.id.nyPizzaCrust);
        if (selected.equals(Flavor.BuildYourOwn) || selected.equals(Flavor.Meatzza)) {
            crustType.setText(R.string.ny_HandTossed_crust);
        } else if (selected.equals(Flavor.Deluxe)) {
            crustType.setText(R.string.ny_Brooklyn_crust);
        } else {
            crustType.setText(R.string.ny_Thin_crust);
        }
    }

    /**
     * to update the price of the current order
     */
    private void updatePrice() {
        Chip chip = findViewById(R.id.nyPriceChip);
        chip.setText("$" + pizza.price());
    }

    /**
     * to set up the pizza flavor dropdown
     */
    private void setupPizzaFlavorSpinner() {
        Spinner flavorSpinner = findViewById(R.id.nyPizzaFlavor);
        ArrayAdapter<CharSequence> pizzaSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.pizza_flavors, android.R.layout.simple_spinner_item);
        pizzaSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flavorSpinner.setAdapter(pizzaSizeAdapter);
        flavorSpinner.setSelection(0);
    }

    /**
     * to set up pizza size dropdown
     */
    private void setupPizzaSizeSpinner() {
        Spinner sizeSpinner = findViewById(R.id.nyPizzaSize);
        ArrayAdapter<CharSequence> pizzaSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.pizza_size_options, android.R.layout.simple_spinner_item);
        pizzaSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(pizzaSizeAdapter);
        sizeSpinner.setSelection(0);
    }

    private ListView toppingsListView;
    private ArrayAdapter toppingsAdapter;

    /**
     * to set up all the toppings
     */
    private void setupToppingsListView() {
        toppingsListView = findViewById(R.id.nyToppingList);
        toppingsListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        toppingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice);
        toppingsListView.setAdapter(toppingsAdapter);
        for (Topping topping: Topping.values()) {
            toppingsAdapter.add(topping);
        }

        final int TOPPINGS_LIMIT = 7;
        int selected[] = { 0 };
        toppingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean status = toppingsListView.isItemChecked(position);
                if (status && selected[0] >= TOPPINGS_LIMIT) {
                    toppingsListView.setItemChecked(position, false);
                    return;
                }

                Topping topping = (Topping) toppingsListView.getItemAtPosition(position);
                if (status) {
                    pizza.add(topping);
                    selected[0] = selected[0] + 1;
                } else {
                    pizza.remove(topping);
                    selected[0] = selected[0] - 1;
                }

                updatePrice();
            }
        });
    }

    /**
     * to select all the toppings
     * @param listView the toppings list view
     */
    private void selectAllListViewItems(ListView listView) {
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            listView.setItemChecked(i, true);
        }
    }

    /**
     * The click listener for the place order button
     */
    private View.OnClickListener orderPizzaListener = new View.OnClickListener() {
        /**
         * Sends the customized pizza to the main activity
         * for it be added to the current order.
         * @param view The button view.
         */
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), R.string.order_placed, Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.putExtra("pizza", pizza);
            setResult(MainActivity.PIZZA_ORDER, intent);
            finish();
        }
    };

}