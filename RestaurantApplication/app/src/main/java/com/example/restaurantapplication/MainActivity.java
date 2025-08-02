package com.example.restaurantapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.restaurantapplication.data.Cuisine;
import com.example.restaurantapplication.data.CuisineRequestForFilter;
import com.example.restaurantapplication.data.CuisineResponse;
import com.example.restaurantapplication.data.CuisineResponseForFilter;
import com.example.restaurantapplication.data.Dish;
import com.example.restaurantapplication.data.DishDetailRequest;
import com.example.restaurantapplication.data.DishDetailResponse;
import com.example.restaurantapplication.data.ItemData;
import com.example.restaurantapplication.data.OrderRequest;
import com.example.restaurantapplication.network.ApiClient;

import com.example.restaurantapplication.network.ApiServiceForOrder;
import com.example.restaurantapplication.network.CuisineApiService;
import com.example.restaurantapplication.network.CuisineApiServiceForFilter;
import com.example.restaurantapplication.network.DishApiService;
import com.example.restaurantapplication.network.RetrofitClient;
import com.example.restaurantapplication.utils.DishUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    Fragment currentFragment = null;
    private final Map<Integer, Supplier<Fragment>> fragmentMap = new HashMap<Integer, Supplier<Fragment>>() {{
        put(R.id.nav_home, HomeFragment::new);
        put(R.id.nav_list, ListFragment::new);
        put(R.id.nav_menu, MenuFragment::new);
        put(R.id.nav_orders, OrdersFragment::new);
    }};
    public static List<Cuisine> allCuisines = new ArrayList<>();
    public static List<Dish> top3Dish = new ArrayList<>();
    public static List<Cuisine> filteredCuisines = new ArrayList<>();
    public static Map<Dish, Integer> cartItems = new LinkedHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Supplier<Fragment> fragmentSupplier = fragmentMap.get(item.getItemId());

            if (fragmentSupplier != null) {
                Fragment selectedFragment = fragmentSupplier.get();

                if (currentFragment == null || !selectedFragment.getClass().equals(currentFragment.getClass())) {
                    loadFragment(selectedFragment);
                }

                return true;
            }

            return false;
        });


        fetchAllPages(1, 10, allCuisines);

        fetchDishFilter(null,0,10000,2);

        List<Integer> itemIds = Arrays.asList(34234294, 34234295, 34234296); // example
        getDishDetailsList(itemIds, new DishListCallback() {
            @Override
            public void onSuccess(List<DishDetailResponse> dishes) {
                for (DishDetailResponse d : dishes) {
                    Log.d("DISH", "Name: " + d.getItem_name() + ", Price: " + d.getItem_price());
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void loadFragment(Fragment fragment) {
        currentFragment = fragment;

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss(); // Helps avoid crash if state already saved
    }

    public boolean isNetworkAvailable(MainActivity context) {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }

        return false;
    }

    private void fetchAllPages(int page, int count, List<Cuisine> accumulatedCuisines) {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
        Map<String, Integer> body = new HashMap<>();
        body.put("page", page);
        body.put("count", count);

        CuisineApiService apiService = RetrofitClient.getClient().create(CuisineApiService.class);


        Call<CuisineResponse> call = apiService.getCuisines(body);
        call.enqueue(new Callback<CuisineResponse>() {
            @Override
            public void onResponse(Call<CuisineResponse> call, Response<CuisineResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    CuisineResponse cuisineResponse = response.body();
                    List<Cuisine> cuisines = cuisineResponse.getCuisines();
                    // Use a Set to avoid duplicate cuisine IDs
                    Set<String> existingCuisineIds = new HashSet<>();
                    for (Cuisine cuisine : accumulatedCuisines) {
                        existingCuisineIds.add(cuisine.getCuisine_id());
                    }
                    for (Cuisine cuisine : cuisines) {
                        if (!existingCuisineIds.contains(cuisine.getCuisine_id())) {
                            accumulatedCuisines.add(cuisine);
                            existingCuisineIds.add(cuisine.getCuisine_id());
                        }
                    }


                    Log.d("CuisineFetch", "Fetched page " + page);

                    if (page < cuisineResponse.getTotal_pages()) {
                        fetchAllPages(page + 1, count, accumulatedCuisines); // Fetch next page
                    } else {
                        top3Dishes(accumulatedCuisines);
                        HomeFragment.lottieAnimationView.setVisibility(View.GONE);
                        HomeFragment.cuisine_list_layout.setVisibility(View.VISIBLE);
                        HomeFragment.top_3_dish_layout.setVisibility(View.VISIBLE);
                        // All data fetched

                        Log.d("CuisineFetch", "Total cuisines fetched: " + accumulatedCuisines.size());
                        Log.d("CuisineFetch", "Total cuisines fetched: " + accumulatedCuisines.toString());
                        // TODO: Update RecyclerView or UI here

                    }
                } else {
                    Log.e("CuisineFetch", "Failed to fetch page " + page);
                }
            }

            @Override
            public void onFailure(Call<CuisineResponse> call, Throwable t) {
                Log.e("CuisineFetch", "Network error: " + t.getMessage(), t);
            }
        });
    }

    private void top3Dishes(List<Cuisine> accumulatedCuisines) {
        List<Dish> topDishes = DishUtils.getTop4RatedDishes(accumulatedCuisines);
        for (Dish dish : topDishes) {
            top3Dish.add(dish);
            Log.d("TopDish", dish.getId() + " " + dish.getName() + " - " + dish.getRating());
        }
    }

    private void fetchDishFilter(List<String> cuisineTypes,int min,int max,int minRating) {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            return;
        }
        CuisineRequestForFilter.PriceRange priceRange = new CuisineRequestForFilter.PriceRange(min, max);

        List<String> types = cuisineTypes != null ? cuisineTypes : new ArrayList<>();

        CuisineRequestForFilter request = new CuisineRequestForFilter(types, priceRange, minRating > 0 ? minRating : null);

        CuisineApiServiceForFilter apiService = RetrofitClient.getClient().create(CuisineApiServiceForFilter.class);
        Call<CuisineResponseForFilter> call = apiService.getFilteredCuisines(request);

        call.enqueue(new Callback<CuisineResponseForFilter>() {
            @Override
            public void onResponse(Call<CuisineResponseForFilter> call, Response<CuisineResponseForFilter> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Cuisine> newFilteredCuisines = response.body().getCuisines();
                    if (newFilteredCuisines != null) {
                        filteredCuisines.clear();
                        filteredCuisines.addAll(newFilteredCuisines);
                    }
                    Log.d("API_SUCCESS", "Fetched " + filteredCuisines.size() + " cuisines");
                    Log.d("API_SUCCESS", "Fetched " + filteredCuisines.toString() + " cuisines");
                } else {
                    Log.e("API_ERROR", "Request failed: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<CuisineResponseForFilter> call, Throwable t) {
                Log.e("API_FAILURE", "Network error: " + t.getMessage(), t);

            }
        });

    }

    public void getDishDetailsList(List<Integer> itemIds, DishListCallback callback) {
        List<DishDetailResponse> resultList = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger remaining = new AtomicInteger(itemIds.size());

        DishApiService apiService = RetrofitClient.getClient().create(DishApiService.class);

        for (Integer id : itemIds) {
            DishDetailRequest request = new DishDetailRequest(id);
            Call<DishDetailResponse> call = apiService.getDishDetails(request);

            call.enqueue(new Callback<DishDetailResponse>() {
                @Override
                public void onResponse(Call<DishDetailResponse> call, Response<DishDetailResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        resultList.add(response.body());
                    } else {
                        Log.e("API_ERROR", "Failed for ID: " + id + ", Code: " + response.code());
                    }

                    if (remaining.decrementAndGet() == 0) {
                        callback.onSuccess(resultList);
                    }
                }

                @Override
                public void onFailure(Call<DishDetailResponse> call, Throwable t) {
                    Log.e("API_FAILURE", "Error for ID: " + id + ", " + t.getMessage());
                    if (remaining.decrementAndGet() == 0) {
                        callback.onSuccess(resultList);
                    }
                }
            });
        }
    }

    private interface DishListCallback {
        void onSuccess(List<DishDetailResponse> dishes);
        void onError(String errorMessage);
    }

    public static void placeOrder(Context context,String total_amount, int total_items, List<ItemData> items){
        // In your activity or repository class

        OrderRequest order = new OrderRequest(total_amount, total_items, items);

        ApiServiceForOrder apiService = ApiClient.getApiService();
        Call<Void> call = apiService.makePayment(
                "uonebancservceemultrS3cg8RaL30",
                "make_payment",
                "application/json",
                order
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context,"Order Placed",Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API_ERROR", "Error code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage(), t);
            }
        });

    }



}
