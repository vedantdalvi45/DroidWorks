package com.example.restaurantapplication.network;

import com.example.restaurantapplication.data.CuisineResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.Map;

public interface CuisineApiService {
    @Headers({
            "Content-Type: application/json",
            "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
                "X-Forward-Proxy-Action: get_item_list"
    })
    @POST("emulator/interview/get_item_list") // Replace with actual endpoint
    Call<CuisineResponse> getCuisines(@Body Map<String, Integer> body);
}
