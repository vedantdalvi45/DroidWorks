package com.example.restaurantapplication.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CuisineRequestForFilter {
    @SerializedName("cuisine_type")
    private List<String> cuisine_type;

    @SerializedName("price_range")
    private PriceRange price_range;

    @SerializedName("min_rating")
    private float min_rating;

    public CuisineRequestForFilter(List<String> cuisine_type, PriceRange price_range, float min_rating) {
        this.cuisine_type = cuisine_type;
        this.price_range = price_range;
        this.min_rating = min_rating;
    }

    public static class PriceRange {
        @SerializedName("min_amount")
        private int min_amount;

        @SerializedName("max_amount")
        private int max_amount;

        public PriceRange(int min_amount, int max_amount) {
            this.min_amount = min_amount;
            this.max_amount = max_amount;
        }
    }
}
