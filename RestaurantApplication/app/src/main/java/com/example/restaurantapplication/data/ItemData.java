package com.example.restaurantapplication.data;

// ItemData.java
public class ItemData {
    private int cuisine_id;
    private int item_id;
    private double item_price;
    private int item_quantity;

    public ItemData(int cuisine_id, int item_id, double item_price, int item_quantity) {
        this.cuisine_id = cuisine_id;
        this.item_id = item_id;
        this.item_price = item_price;
        this.item_quantity = item_quantity;
    }
    // Static factory method to create ItemData from Dish and quantity
    public static ItemData fromDish(Dish dish, int quantity) {
        int cuisineId = 0;
        int itemId = 0;
        double price = 0.0;

        try {
            cuisineId = Integer.parseInt(dish.getCuisine_id());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            itemId = Integer.parseInt(dish.getId());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            price = Double.parseDouble(dish.getPrice());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return new ItemData(cuisineId, itemId, price, quantity);
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "cuisine_id=" + cuisine_id +
                ", item_id=" + item_id +
                ", item_price=" + item_price +
                ", item_quantity=" + item_quantity +
                '}';
    }
}

