package com.example.restaurantapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.restaurantapplication.adapters.RecyclerViewCuisineCategoryAdapter;
import com.example.restaurantapplication.adapters.Top3Adapter;


public class HomeFragment extends Fragment {

    private RecyclerView cusineCategoryRecyclerView;
    public static LottieAnimationView lottieAnimationView;
    private RelativeLayout retry_relative;
    private LottieAnimationView retry_anim;
    RecyclerView top3RecyclerView;

    public static RelativeLayout cuisine_list_layout;
    public static LinearLayout top_3_dish_layout;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lottieAnimationView = view.findViewById(R.id.loading_anim);

        cuisine_list_layout = view.findViewById(R.id.cuisine_list_layout);
        top_3_dish_layout = view.findViewById(R.id.top_3_dish_layout);

        if (!MainActivity.allCuisines.isEmpty()) {
            lottieAnimationView.setVisibility(View.GONE);
            cuisine_list_layout.setVisibility(View.VISIBLE);
            top_3_dish_layout.setVisibility(View.VISIBLE);

        }

        this.cuisineCategoryCards(view);


        return view;
    }


    private void cuisineCategoryCards(View view) {
        cusineCategoryRecyclerView = view.findViewById(R.id.cuisineCategory_items);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        cusineCategoryRecyclerView.setLayoutManager(layoutManager);
        RecyclerViewCuisineCategoryAdapter cuisineAdapter =
                new RecyclerViewCuisineCategoryAdapter(MainActivity.allCuisines, getContext());
        cusineCategoryRecyclerView.setAdapter(cuisineAdapter);

        top3RecyclerView = view.findViewById(R.id.top_3);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        top3RecyclerView.setLayoutManager(layoutManager2);
        Top3Adapter top3Adapter = new Top3Adapter(MainActivity.top3Dish, getContext());
        top3RecyclerView.setAdapter(top3Adapter);
    }




}