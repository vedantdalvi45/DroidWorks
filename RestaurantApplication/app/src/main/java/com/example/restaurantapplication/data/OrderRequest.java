package com.example.restaurantapplication.data;

// OrderRequest.java
import java.util.List;

public class OrderRequest {
    private String total_amount;
    private int total_items;
    private List<ItemData> data;

    public OrderRequest(String total_amount, int total_items, List<ItemData> data) {
        this.total_amount = total_amount;
        this.total_items = total_items;
        this.data = data;
    }
}

