package com.example.restaurantapplication.utils;

import com.example.restaurantapplication.data.Cuisine;
import com.example.restaurantapplication.data.Dish;

import java.util.*;

public class DishUtils {

    public static List<Dish> getTop4RatedDishes(List<Cuisine> cuisines) {
        PriorityQueue<Dish> topDishes = new PriorityQueue<>(4, new Comparator<Dish>() {
            @Override
            public int compare(Dish d1, Dish d2) {
                return Float.compare(Float.parseFloat(d1.getRating()), Float.parseFloat(d2.getRating()));
            }
        });

        for (Cuisine cuisine : cuisines) {
            for (Dish dish : cuisine.getItems()) {
                topDishes.offer(dish);
                if (topDishes.size() > 4) {
                    topDishes.poll(); // remove lowest rated
                }
            }
        }

        // Now contains top 4, but in ascending order
        List<Dish> result = new ArrayList<>(topDishes);
        result.sort((d1, d2) -> Float.compare(Float.parseFloat(d2.getRating()), Float.parseFloat(d1.getRating()))); // descending

        return result;
    }
}

