package com.example.messdays.data;

import java.time.LocalDate;

public class MessDayEvent {
    private LocalDate date;
    private boolean hasLunch;
    private boolean hasDinner;
    private String event;

    public MessDayEvent(LocalDate date, boolean hasLunch, boolean hasDinner, String event) {
        this.date = date;
        this.hasLunch = hasLunch;
        this.hasDinner = hasDinner;
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isHasLunch() {
        return hasLunch;
    }

    public void setHasLunch(boolean hasLunch) {
        this.hasLunch = hasLunch;
    }

    public boolean isHasDinner() {
        return hasDinner;
    }

    public void setHasDinner(boolean hasDinner) {
        this.hasDinner = hasDinner;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "MessDayEvent{" +
                "date=" + date +
                ", hasLunch=" + hasLunch +
                ", hasDinner=" + hasDinner +
                ", event='" + event + '\'' +
                '}';
    }

    public int getMealPrice() {
        int price = 0;
        if (hasLunch) {
            price+=60;
        }
        if (hasDinner) {
            price+=60;
        }
        return price;
    }
}
