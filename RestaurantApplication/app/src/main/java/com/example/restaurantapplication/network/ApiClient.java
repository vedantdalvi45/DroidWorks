package com.example.restaurantapplication.network;

// ApiClient.java
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://uat.onebanc.ai/";
    private static Retrofit retrofit = null;

    public static ApiServiceForOrder getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiServiceForOrder.class);
    }
}

