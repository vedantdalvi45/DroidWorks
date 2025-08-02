package com.example.restaurantapplication.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Cart {
    private List<Dish> dishes;
    private double price;
    private String date;


    public Cart(List<Dish> dishes, double price) {
        this.dishes = dishes;
        this.price = price;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        this.date = sdf.format(new Date());
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
