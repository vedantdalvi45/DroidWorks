package com.example.restaurantapplication.adapters;

import static com.example.restaurantapplication.ListFragment.scrollRecyclerViewByExactDp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantapplication.ListFragment;
import com.example.restaurantapplication.MainActivity;
import com.example.restaurantapplication.R;
import com.example.restaurantapplication.data.Cuisine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewCuisineCategoryAdapter extends RecyclerView.Adapter<RecyclerViewCuisineCategoryAdapter.CuisineViewHolder>{
    private List<Cuisine> cuisineCategoryList;

    private Context context;

    public RecyclerViewCuisineCategoryAdapter(List<Cuisine> cuisineCategoryList, Context context) {
        this.cuisineCategoryList = cuisineCategoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CuisineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_card,parent,false);
        CuisineViewHolder cuisineViewHolder = new CuisineViewHolder(view);
        return cuisineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CuisineViewHolder holder, int position) {
        holder.setData(cuisineCategoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return cuisineCategoryList.size();
    }

    public void updateData(List<Cuisine> accumulatedCuisines) {
        this.cuisineCategoryList = accumulatedCuisines;
        notifyDataSetChanged();
    }

    class CuisineViewHolder extends RecyclerView.ViewHolder{

        private TextView cuisineName;
        private ImageView cuisineImage;
        public CuisineViewHolder(@NonNull View itemView) {
            super(itemView);
            cuisineName = itemView.findViewById(R.id.cuisineTitle);
            cuisineImage = itemView.findViewById(R.id.cuisineImage);
        }

        public void setData(Cuisine cuisineCategory) {
            cuisineName.setText(cuisineCategory.getCuisine_name());
            Glide.with(this.itemView.getContext())
                    .load(cuisineCategory.getCuisine_image_url())
                    .into(this.cuisineImage);
            Map<String,Integer> scrollDP = new LinkedHashMap<>();


            int total = 0;
            int currentItemCount;
            for (Cuisine cuisine : cuisineCategoryList){

                scrollDP.put(cuisine.getCuisine_name(), total);
                currentItemCount = cuisine.getItems().size();
                total += 45;
                total += (currentItemCount * 110);
            }
            Log.d("scrollDP",String.valueOf(scrollDP));

            cuisineImage.setOnClickListener(view -> {
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_list);
                // Navigate to ListFragment
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ListFragment listFragment = new ListFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, listFragment)
                        .addToBackStack("HomeFragment")
                        .commit();
                activity.getSupportFragmentManager().executePendingTransactions();

                if (listFragment.getView() != null) {
                    if (scrollDP.containsKey(cuisineCategory.getCuisine_name())) {
                        scrollRecyclerViewByExactDp(ListFragment.rvCuisineList, scrollDP.get(cuisineCategory.getCuisine_name()), scrollDP.get(cuisineCategory.getCuisine_name()), 10);
                    }
                }
            });


        }

    }
}
