package com.weatherforecast.models;


import com.orm.SugarRecord;

public class Coord extends SugarRecord{
    private String lon;
    private String lat;

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Coord(String lon, String lat) {

        this.lon = lon;
        this.lat = lat;
    }
}
