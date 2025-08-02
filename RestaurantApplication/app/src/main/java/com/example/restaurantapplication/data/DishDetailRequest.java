package com.example.restaurantapplication.data;

public class DishDetailRequest {
    private int item_id;

    public DishDetailRequest(int item_id) {
        this.item_id = item_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}
