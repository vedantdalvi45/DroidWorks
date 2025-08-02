package com.example.restaurantapplication.data;

public class Dish {
    private String id;
    private String name;
    private String image_url;
    private String price;
    private String rating;
    private String cuisine_id;  // This will be set by Cuisine

    public Dish(String id, String name, String image_url, String price, String rating) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.price = price;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCuisine_id() {
        return cuisine_id;
    }

    public void setCuisine_id(String cuisine_id) {
        this.cuisine_id = cuisine_id;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", price='" + price + '\'' +
                ", rating='" + rating + '\'' +
                ", cuisine_id='" + cuisine_id + '\'' +
                '}';
    }
}
