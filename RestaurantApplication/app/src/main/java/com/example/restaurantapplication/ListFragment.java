package com.example.restaurantapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.SearchView;

import com.example.restaurantapplication.adapters.CuisineAdapter;
import com.example.restaurantapplication.data.Cuisine;
import com.example.restaurantapplication.data.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ListFragment extends Fragment {

    private CuisineAdapter adapter;
    public static RecyclerView rvCuisineList;
    SearchView searchView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Rename and change types of parameters


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        MainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_home);
                        // Navigate to HomeFragment
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        HomeFragment homeFragment = new HomeFragment();
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, homeFragment)
                                .addToBackStack(null) // Optional: Add to back stack
                                .commit();
                    }
                }
        );

        ImageView filterButton = view.findViewById(R.id.ivFilter);
        filterButton.setOnClickListener(v -> {
                    FilterDialogFragment filterDialog = new FilterDialogFragment();
                    filterDialog.setFilterDialogListener((cuisines, minPrice, maxPrice, minRating) -> {
                        // Handle filter applied here
                        Log.d("FILTER", "Cuisines: " + cuisines);
                        Log.d("FILTER", "Price range: " + minPrice + " - " + maxPrice);
                        Log.d("FILTER", "Min rating: " + minRating);

                    });

                    filterDialog.show(getChildFragmentManager(), "filter_dialog");
                }
        );

        AutoCompleteTextView searchBar = view.findViewById(R.id.search_bar);

        Map<String,String> dishesMap = new TreeMap<>();
        for (Cuisine cuisine : MainActivity.allCuisines){
            for (Dish d : cuisine.getItems()){
                dishesMap.put(d.getName(),d.getId());
            }
        }
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, new ArrayList<>(dishesMap.keySet())
        );
        searchBar.setAdapter(searchAdapter);


        // Optional: Show suggestions when focused
        searchBar.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchBar.showDropDown();
            }
        });

        rvCuisineList = view.findViewById(R.id.rvCuisineList);
        rvCuisineList.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("filterd",String.valueOf(MainActivity.allCuisines.toString()));
        adapter = new CuisineAdapter(MainActivity.allCuisines,getContext());
        rvCuisineList.setAdapter(adapter);



        return view;
    }


    public static void scrollRecyclerViewByExactDp(@NonNull RecyclerView recyclerView, float dpToScroll, int stepPx, int delayMs) {
        Handler handler = new Handler();

        float targetPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dpToScroll, recyclerView.getResources().getDisplayMetrics()
        );

        final float[] scrolledPx = {0f};

        Runnable scrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (scrolledPx[0] < targetPx) {
                    float remaining = targetPx - scrolledPx[0];
                    int scrollBy = (int) Math.min(stepPx, remaining); // Prevent overscroll

                    recyclerView.scrollBy(0, scrollBy);
                    scrolledPx[0] += scrollBy;

                    if (scrolledPx[0] < targetPx) {
                        handler.postDelayed(this, delayMs);
                    }
                }
            }
        };

        handler.post(scrollRunnable);
    }

}