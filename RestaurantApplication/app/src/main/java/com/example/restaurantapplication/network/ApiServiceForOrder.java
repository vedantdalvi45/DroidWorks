package com.example.restaurantapplication.network;

// ApiService.java
import com.example.restaurantapplication.data.OrderRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiServiceForOrder {

    @POST("emulator/interview/make_payment")
    Call<Void> makePayment(
            @Header("X-Partner-API-Key") String apiKey,
            @Header("X-Forward-Proxy-Action") String proxyAction,
            @Header("Content-Type") String contentType,
            @Body OrderRequest orderRequest
    );
}
