package com.weatherforecast.models;

import com.orm.SugarRecord;

public class Notification extends SugarRecord {

    private String name;
    private String temp;
    private String diff;
    private String date;

    public Notification() {}

    public Notification(String name, String temp, String diff, String date) {
        this.name = name;
        this.temp = temp;
        this.diff = diff;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
