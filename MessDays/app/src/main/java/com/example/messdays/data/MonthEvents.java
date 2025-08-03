package com.example.messdays.data;

import java.util.List;

public class MonthEvents {
    private String month;
    private List<MessDayEvent> messDayEventList;

    public MonthEvents(String month, List<MessDayEvent> messDayEventList) {
        this.month = month;
        this.messDayEventList = messDayEventList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<MessDayEvent> getMessDayEventList() {
        return messDayEventList;
    }

    public void setMessDayEventList(List<MessDayEvent> messDayEventList) {
        this.messDayEventList = messDayEventList;
    }
}
