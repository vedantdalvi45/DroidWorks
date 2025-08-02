package com.example.restaurantapplication.network;

import com.example.restaurantapplication.data.CuisineRequestForFilter;
import com.example.restaurantapplication.data.CuisineResponseForFilter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CuisineApiServiceForFilter {
    @Headers({
            "Content-Type: application/json",
            "X-Partner-API-Key: uonebancservceemultrS3cg8RaL30",
            "X-Forward-Proxy-Action: get_item_by_filter"
    })
    @POST("emulator/interview/get_item_by_filter")
    Call<CuisineResponseForFilter> getFilteredCuisines(@Body CuisineRequestForFilter request);
}
