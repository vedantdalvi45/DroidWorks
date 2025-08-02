package com.example.restaurantapplication.data;

import java.util.List;

public class CuisineResponseForFilter {
    private int response_code;
    private int outcome_code;
    private String response_message;
    private List<Cuisine> cuisines;

    public CuisineResponseForFilter(int response_code, int outcome_code, String response_message, List<Cuisine> cuisines) {
        this.response_code = response_code;
        this.outcome_code = outcome_code;
        this.response_message = response_message;
        this.cuisines = cuisines;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public int getOutcome_code() {
        return outcome_code;
    }

    public void setOutcome_code(int outcome_code) {
        this.outcome_code = outcome_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }
}
