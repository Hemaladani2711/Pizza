package com.example.pizza;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Custom pizza adapter to show the list of pizza in the RecyclerView.
 * @author Jit Patel
 * @author Tejas Shah
 */
public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.ViewHolder> {
    private ArrayList<Pizza> pizzaList;
    private ArrayList<Boolean> selectedPizzas = new ArrayList<>();
    private boolean hideCheckMarks = false;

    /**
     * to create the Pizza adapter.
     * @param pizzaList The list of pizza
     */
    public PizzaAdapter(ArrayList<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
        if (this.pizzaList == null) {
            this.pizzaList = new ArrayList<>();
        }
    }

    /**
     * to create the Pizza adapter.
     * @param pizzaList The list of pizza
     * @param hideCheckMarks Weather to hide the selection check marks.
     */
    public PizzaAdapter(ArrayList<Pizza> pizzaList, boolean hideCheckMarks) {
        this.pizzaList = pizzaList;
        this.hideCheckMarks = hideCheckMarks;
        if (this.pizzaList == null) {
            this.pizzaList = new ArrayList<>();
        }
    }

    /**
     * the ViewHolder of the PizzaAdapter.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckedTextView pizzaListTextView;
        private int position;

        /**
         * to create the view holder.
         * @param itemView The item view.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaListTextView = itemView.findViewById(R.id.pizzaListTextView);

            if (hideCheckMarks) {
                pizzaListTextView.setCheckMarkDrawable(null);
            }

            selectedPizzas.add(false);
            pizzaListTextView.setOnClickListener(new View.OnClickListener() {
                /**
                 * to handle the selection of the current pizza item
                 * @param v The view
                 */
                @Override
                public void onClick(View v) {
                    pizzaListTextView.toggle();
                    selectedPizzas.set(position, pizzaListTextView.isChecked());
                }
            });
        }

        /**
         * to get the pizza list checked text view.
         * @return pizza list checked text view
         */
        public CheckedTextView getPizzaListTextView() {
            return pizzaListTextView;
        }

        /**
         * to set the position of the item
         * @param position position
         */
        public void setPosition(int position) {
            this.position = position;
        }
    }

    /**
     * to create the view holder for the item
     * @param parent parent.
     * @param viewType view type.
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pizza_list, parent, false);

        return new ViewHolder(view);
    }

    /**
     * to bind the data to the view holder.
     * @param holder The view holder.
     * @param position The position.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = pizzaList.get(position).toString();
        holder.getPizzaListTextView().setText(text);
        holder.setPosition(position);
    }

    /**
     * to remove the pizza at the position.
     * @param position The position.
     */
    public void remove(int position) {
        if (position >= pizzaList.size()) {
            return;
        }

        pizzaList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, pizzaList.size());
    }

    /**
     * to remove all the selected pizzas.
     */
    public int removeSelectedPizzas() {
        int total = 0;
        for (int i = selectedPizzas.size() - 1; i >= 0; i--) {
            if (selectedPizzas.get(i)) {
                total += 1;
                remove(i);
            }
        }

        return total;
    }

    /**
     * to remove all pizzas in the list.
     */
    public void removeAll() {
        for (int i = selectedPizzas.size() - 1; i >= 0; i--) {
            remove(i);
        }
    }

    /**
     * to return the total item count.
     * @return item count.
     */
    @Override
    public int getItemCount() {
        return pizzaList.size();
    }
}
