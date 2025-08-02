package com.example.restaurantapplication.adapters;

import static com.example.restaurantapplication.MainActivity.cartItems;

import android.content.Context;
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
import com.example.restaurantapplication.R;
import com.example.restaurantapplication.data.Dish;

import java.util.List;

public class Top3Adapter extends RecyclerView.Adapter<Top3Adapter.TopDishAdapter>{

    private List<Dish> top3Dishes;

    private Context context;

    public Top3Adapter(List<Dish> top3Dishes, Context context) {
        this.top3Dishes = top3Dishes;
        this.context = context;
    }

    @NonNull
    @Override
    public TopDishAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dish_card,parent,false);
        TopDishAdapter topDishAdapter = new TopDishAdapter(view);
        return topDishAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull TopDishAdapter holder, int position) {
        holder.setData(top3Dishes.get(position));
    }

    @Override
    public int getItemCount() {
        return top3Dishes.size();
    }

    static class TopDishAdapter extends RecyclerView.ViewHolder{
        ImageView dishImage;
        TextView dishName;
        TextView dishRating;
        TextView quantity;
        ImageButton increase, decrease;
        private TopDishAdapter(@NonNull View itemView) {
            super(itemView);

            dishImage = itemView.findViewById(R.id.image_food);
            dishName = itemView.findViewById(R.id.text_dish_name);
            dishRating = itemView.findViewById(R.id.text_rating);
            quantity = itemView.findViewById(R.id.text_quantity);
            increase = itemView.findViewById(R.id.button_increase);
            decrease = itemView.findViewById(R.id.button_decrease);


        }

        private void setData(Dish dish){
            Glide.with(this.itemView.getContext())
                    .load(dish.getImage_url())
                    .into(this.dishImage);
            dishName.setText(dish.getName());
            dishRating.setText(dish.getRating());
            if (MainActivity.cartItems.containsKey(dish)) {
                quantity.setText(String.valueOf(MainActivity.cartItems.get(dish)));
            } else {
                quantity.setText("0");
            }
            increase.setOnClickListener(view -> {
                cartItems.put(dish, cartItems.getOrDefault(dish, 0) + 1);
                quantity.setText(String.valueOf(cartItems.get(dish)));
            });
            decrease.setOnClickListener(view -> {
                if (cartItems.containsKey(dish)) {
                    if (cartItems.get(dish) <= 1) {
                        cartItems.remove(dish);
                        quantity.setText("0");
                    } else {
                        cartItems.put(dish, cartItems.get(dish) - 1);
                        quantity.setText(String.valueOf(cartItems.get(dish)));
                    }
                }
            });
        }
    }
}
