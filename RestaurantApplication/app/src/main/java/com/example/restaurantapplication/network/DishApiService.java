package com.example.restaurantapplication.network;

import com.example.restaurantapplication.data.DishDetailRequest;
import com.example.restaurantapplication.data.DishDetailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DishApiService {

    @Headers({
            "Content-Type: application/json",
            "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
            "X-Forward-Proxy-Action: get_item_by_id"
    })
    @POST("emulator/interview/get_item_by_id") // Replace with actual endpoint
    Call<DishDetailResponse> getDishDetails(@Body DishDetailRequest request);

}

