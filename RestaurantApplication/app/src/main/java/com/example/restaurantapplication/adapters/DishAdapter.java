package com.example.restaurantapplication.adapters;

import static com.example.restaurantapplication.MainActivity.cartItems;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.restaurantapplication.R;
import com.example.restaurantapplication.data.Dish;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<Dish> dishes;


    public DishAdapter(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishes.get(position);
        holder.tvDishName.setText(dish.getName());

        holder.tvDishPrice.setText("₹" + dish.getPrice());

        Glide.with(holder.itemView.getContext())
                .load(dish.getImage_url())
                .into(holder.ivDishImage);
        holder.tvRating.setText(String.valueOf(dish.getRating()));

        if (cartItems.containsKey(dish)) {
            holder.tvItemCount.setText(String.valueOf(cartItems.get(dish)));
        } else {
            holder.tvItemCount.setText("0");
        }

        holder.btnIncrease.setOnClickListener(view -> {
            cartItems.put(dish, cartItems.getOrDefault(dish, 0) + 1);
            holder.tvItemCount.setText(String.valueOf(cartItems.get(dish)));

        });
        holder.btnDecrease.setOnClickListener(view -> {
            if (cartItems.containsKey(dish)) {
                int currentCount = cartItems.get(dish);
                if (currentCount <= 1) {
                    cartItems.remove(dish);
                    holder.tvItemCount.setText("0");
                } else {
                    cartItems.put(dish, currentCount - 1);
                    holder.tvItemCount.setText(String.valueOf(cartItems.get(dish)));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {
        TextView tvDishName, tvDishPrice , tvRating,tvItemCount;
        ImageView ivDishImage;
        ImageButton btnIncrease, btnDecrease;


        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvDishPrice = itemView.findViewById(R.id.tvDishPrice);
            ivDishImage = itemView.findViewById(R.id.ivDishImage);
            tvRating = itemView.findViewById(R.id.text_rating);
            tvItemCount = itemView.findViewById(R.id.tvItemCount);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}

