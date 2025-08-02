package com.example.restaurantapplication;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.Gravity;

import com.example.restaurantapplication.adapters.CartAdapter;
import com.example.restaurantapplication.adapters.CuisineAdapter;
import com.example.restaurantapplication.adapters.DishAdapter;
import com.example.restaurantapplication.data.Cuisine;
import com.example.restaurantapplication.data.Dish;
import com.example.restaurantapplication.data.ItemData;
import com.example.restaurantapplication.data.OrderRequest;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    private RecyclerView rvDishes;

    private CartAdapter dishAdapter;

    public static List<Dish> cartDishes;

    public static TextView total;
    public static TextView netTotal;
    public static TextView cgst;
    public static TextView sgst;
    public static double subtotalAmount = 0.0;

    private AppCompatButton btnPlaceOrder;
    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        MainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_home);
                        // Navigate to HomeFragment
                        AppCompatActivity activity = (AppCompatActivity) getView().getContext();
                        HomeFragment homeFragment = new HomeFragment();
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, homeFragment)
                                .addToBackStack(null) // Optional: Add to back stack
                                .commit();
                    }
                }
        );
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        cartDishes = new ArrayList<>(MainActivity.cartItems.keySet());
        Log.d("dish,",cartDishes.toString());
        rvDishes = view.findViewById(R.id.cart_recycler_view);
        rvDishes.setLayoutManager(new LinearLayoutManager(getContext()));
        dishAdapter = new CartAdapter();
        rvDishes.setAdapter(dishAdapter);

        total = view.findViewById(R.id.grandTotalAmount);
        netTotal = view.findViewById(R.id.netTotalAmount);
        cgst = view.findViewById(R.id.cgstAmount);
        sgst = view.findViewById(R.id.sgstAmount);

        btnPlaceOrder = view.findViewById(R.id.btn_place_order);


        // Calculate and set the total

        for (Dish dish : cartDishes) {
            // Consider adding a check for MainActivity.cartItems.get(dish) to avoid NullPointerException if a dish is not in the cart.
            subtotalAmount += Double.parseDouble(dish.getPrice()) * MainActivity.cartItems.get(dish);
        }
        setTotal(subtotalAmount);


        btnPlaceOrder.setOnClickListener((view2) -> {
            double cgstAmount = subtotalAmount * 0.025;
            double sgstAmount = subtotalAmount * 0.025;
            double totalAmount = subtotalAmount + cgstAmount + sgstAmount;

            List<ItemData> itemDataList = new ArrayList<>();

            if (cartDishes == null) return; // Modified to just return, as the method signature is void.

            for (Cuisine c : MainActivity.allCuisines){
                for (Dish d : c.getItems()){
                    d.setCuisine_id(c.getCuisine_id());
                }
            }

            for (Dish dish : cartDishes) {

                Integer quantityObj = MainActivity.cartItems.get(dish);
                int quantity = (quantityObj != null) ? quantityObj : 0;

                ItemData item = ItemData.fromDish(dish, quantity);
                itemDataList.add(item);
            }
            Log.d("itemDataList",Double.toString(totalAmount)+"->"+MainActivity.cartItems.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum()+" - "+itemDataList.toString() );
            MainActivity.placeOrder(getContext(), Double.toString(totalAmount), MainActivity.cartItems.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum(),itemDataList);

            LayoutInflater inflater2 = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater2.inflate(R.layout.popup_order, null);

            PopupWindow popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

// Auto-close after 2 seconds
            new Handler().postDelayed(popupWindow::dismiss, 2000);


            MainActivity.cartItems.clear();
            cartDishes.clear();
            subtotalAmount = 0.0;
            setTotal(subtotalAmount);
            dishAdapter.notifyDataSetChanged();
            MainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_home);
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            HomeFragment homeFragment = new HomeFragment();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, homeFragment)
                    .addToBackStack(null) // Optional: Add to back stack
                    .commit();
        });
        return view;
    }

    public static void setTotal(double subtotalAmount){
        double cgstAmount = subtotalAmount * 0.025;
        double sgstAmount = subtotalAmount * 0.025;
        double totalAmount = subtotalAmount + cgstAmount + sgstAmount;
        netTotal.setText(String.format("₹%.2f", subtotalAmount));
        cgst.setText("₹" + String.format("%.2f", cgstAmount));
        sgst.setText("₹" + String.format("%.2f", sgstAmount));
        total.setText(String.format("₹%.2f", totalAmount));
    }
}