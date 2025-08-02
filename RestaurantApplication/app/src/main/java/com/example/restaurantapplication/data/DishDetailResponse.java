package com.example.restaurantapplication.data;

public class DishDetailResponse {
    private int response_code;
    private int outcome_code;
    private String response_message;
    private String cuisine_id;
    private String cuisine_name;
    private String cuisine_image_url;
    private int item_id;
    private String item_name;
    private int item_price;
    private float item_rating;
    private String item_image_url;

    // Getters
    public int getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public int getItem_price() {
        return item_price;
    }

    public float getItem_rating() {
        return item_rating;
    }

    public String getItem_image_url() {
        return item_image_url;
    }

    public String getCuisine_id() {
        return cuisine_id;
    }

    public String getCuisine_name() {
        return cuisine_name;
    }

    public String getCuisine_image_url() {
        return cuisine_image_url;
    }

    @Override
    public String toString() {
        return "DishDetailResponse{" +
                "response_code=" + response_code +
                ", outcome_code=" + outcome_code +
                ", response_message='" + response_message + '\'' +
                ", cuisine_id='" + cuisine_id + '\'' +
                ", cuisine_name='" + cuisine_name + '\'' +
                ", cuisine_image_url='" + cuisine_image_url + '\'' +
                ", item_id=" + item_id +
                ", item_name='" + item_name + '\'' +
                ", item_price=" + item_price +
                ", item_rating=" + item_rating +
                ", item_image_url='" + item_image_url + '\'' +
                '}';
    }
}

