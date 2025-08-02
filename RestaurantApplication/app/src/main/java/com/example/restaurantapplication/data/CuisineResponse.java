package com.example.restaurantapplication.data;

import java.util.List;

public class CuisineResponse {
    private int response_code;
    private int outcome_code;
    private String response_message;
    private int page;
    private int count;
    private int total_pages;
    private int total_items;
    private List<Cuisine> cuisines;
    private String timestamp;
    private String requester_ip;
    private String timetaken;

    public CuisineResponse(int response_code, int outcome_code, String response_message, int page, int count, int total_pages, int total_items, List<Cuisine> cuisines, String timestamp, String requester_ip, String timetaken) {
        this.response_code = response_code;
        this.outcome_code = outcome_code;
        this.response_message = response_message;
        this.page = page;
        this.count = count;
        this.total_pages = total_pages;
        this.total_items = total_items;
        this.cuisines = cuisines;
        this.timestamp = timestamp;
        this.requester_ip = requester_ip;
        this.timetaken = timetaken;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_items() {
        return total_items;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequester_ip() {
        return requester_ip;
    }

    public void setRequester_ip(String requester_ip) {
        this.requester_ip = requester_ip;
    }

    public String getTimetaken() {
        return timetaken;
    }

    public void setTimetaken(String timetaken) {
        this.timetaken = timetaken;
    }

    // Getters and setters
}

