package com.example.restaurantapplication.adapters;

import static com.example.restaurantapplication.MainActivity.cartItems;
import static com.example.restaurantapplication.MenuFragment.subtotalAmount;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantapplication.MainActivity;
import com.example.restaurantapplication.MenuFragment;
import com.example.restaurantapplication.R;
import com.example.restaurantapplication.data.Dish;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CarViewHolder> {



    public CartAdapter() {
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Dish dish = MenuFragment.cartDishes.get(position);
        Log.d("dish",MenuFragment.cartDishes.toString());
        holder.setData(dish, holder, this);
    }

    @Override
    public int getItemCount() {
        return MenuFragment.cartDishes.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView dishImage;
        TextView dishName, price, count, rating;
        ImageButton increase, decrease, delete;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.image_food);
            dishName = itemView.findViewById(R.id.text_name);
            price = itemView.findViewById(R.id.text_price);
            rating = itemView.findViewById(R.id.text_rating);
            count = itemView.findViewById(R.id.text_quantity);
            increase = itemView.findViewById(R.id.button_increase);
            decrease = itemView.findViewById(R.id.button_decrease);
            delete = itemView.findViewById(R.id.button_delete);
        }

        private void setData(Dish dish, CarViewHolder holder, CartAdapter adapter) {
            dishName.setText(dish.getName());
            rating.setText(dish.getRating());
            price.setText("₹ "+dish.getPrice());
            Integer dishCount = MainActivity.cartItems.get(dish);

            if (cartItems.containsKey(dish)) {
                holder.count.setText(String.valueOf(cartItems.get(dish)));
            } else {
                holder.count.setText("0");
            }

            Glide.with(holder.itemView.getContext())
                    .load(dish.getImage_url())
                    .into(holder.dishImage);

            holder.increase.setOnClickListener(view -> {
                cartItems.put(dish, cartItems.getOrDefault(dish, 0) + 1);
                holder.count.setText(String.valueOf(cartItems.get(dish)));

                subtotalAmount += Double.parseDouble(dish.getPrice());
                MenuFragment.setTotal(subtotalAmount);


            });
            decrease.setOnClickListener(view -> {
                int currentCount = Integer.parseInt(holder.count.getText().toString());
                if (currentCount > 1) {
                    currentCount--;
                    holder.count.setText(String.valueOf(currentCount));
                    MainActivity.cartItems.put(dish, currentCount);


                    subtotalAmount -= Double.parseDouble(dish.getPrice());
                    MenuFragment.setTotal(subtotalAmount);
                } else if (currentCount == 1) { // Only remove if count is 1

                    subtotalAmount -= Double.parseDouble(dish.getPrice());
                    MenuFragment.setTotal(subtotalAmount);

                    int removePosition = getAdapterPosition();
                    MainActivity.cartItems.remove(dish);
                    MenuFragment.cartDishes.remove(dish);
                    if (removePosition != RecyclerView.NO_POSITION) {
                        adapter.notifyItemRemoved(removePosition);
                        adapter.notifyItemRangeChanged(removePosition, MenuFragment.cartDishes.size());
                    }
                }

            });
            delete.setOnClickListener(view -> {

                subtotalAmount -= Double.parseDouble(dish.getPrice()) * MainActivity.cartItems.get(dish);
                MenuFragment.setTotal(subtotalAmount);


                int removePosition = getAdapterPosition();
                MainActivity.cartItems.remove(dish);
                MenuFragment.cartDishes.remove(dish);
                adapter.notifyItemRemoved(getAdapterPosition());



            });
        }
    }
}
