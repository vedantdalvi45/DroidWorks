package com.example.restaurantapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapplication.R;
import com.example.restaurantapplication.data.Cuisine;


import java.util.List;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder> {

    private List<Cuisine> cuisineList;

    private Context context;

    public CuisineAdapter(List<Cuisine> cuisineList,Context context) {
        this.cuisineList = cuisineList;
        this.context = context;
    }

    @NonNull
    @Override
    public CuisineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cuisine, parent, false);
        return new CuisineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuisineViewHolder holder, int position) {
        Cuisine cuisine = cuisineList.get(position);
        holder.tvCuisineName.setText(cuisine.getCuisine_name());

        // Set up inner RecyclerView for dishes
        DishAdapter dishAdapter = new DishAdapter(cuisine.getItems());
        holder.rvDishes.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.rvDishes.setAdapter(dishAdapter);
    }

    @Override
    public int getItemCount() {
        return cuisineList.size();
    }

    static class CuisineViewHolder extends RecyclerView.ViewHolder {
        TextView tvCuisineName;
        RecyclerView rvDishes;

        public CuisineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCuisineName = itemView.findViewById(R.id.tvCuisineName);
            rvDishes = itemView.findViewById(R.id.rvDishes);
        }
    }
}

