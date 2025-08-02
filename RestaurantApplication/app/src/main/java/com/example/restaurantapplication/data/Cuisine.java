package com.example.restaurantapplication.data;

import java.util.List;

public class Cuisine {
    private String cuisine_id;
    private String cuisine_name;
    private String cuisine_image_url;
    private List<Dish> items;

    public Cuisine(String cuisine_id, String cuisine_name, String cuisine_image_url, List<Dish> items) {
        this.cuisine_id = cuisine_id;
        this.cuisine_name = cuisine_name;
        this.cuisine_image_url = cuisine_image_url;
        this.items = items;

        // Set cuisine_id on each Dish
        if (items != null) {
            for (Dish dish : items) {
                dish.setCuisine_id(cuisine_id);
            }
        }
    }

    public String getCuisine_id() {
        return cuisine_id;
    }

    public void setCuisine_id(String cuisine_id) {
        this.cuisine_id = cuisine_id;

        // Update cuisine_id for all dishes when cuisine_id changes
        if (this.items != null) {
            for (Dish dish : items) {
                dish.setCuisine_id(cuisine_id);
            }
        }
    }

    public String getCuisine_name() {
        return cuisine_name;
    }

    public void setCuisine_name(String cuisine_name) {
        this.cuisine_name = cuisine_name;
    }

    public String getCuisine_image_url() {
        return cuisine_image_url;
    }

    public void setCuisine_image_url(String cuisine_image_url) {
        this.cuisine_image_url = cuisine_image_url;
    }

    public List<Dish> getItems() {
        return items;
    }

    public void setItems(List<Dish> items) {
        this.items = items;

        if (items != null) {
            for (Dish dish : items) {
                dish.setCuisine_id(this.cuisine_id);
            }
        }
    }

    @Override
    public String toString() {
        return "Cuisine{" +
                "cuisine_id='" + cuisine_id + '\'' +
                ", cuisine_name='" + cuisine_name + '\'' +
                ", cuisine_image_url='" + cuisine_image_url + '\'' +
                ", items=" + items +
                '}';
    }
}
