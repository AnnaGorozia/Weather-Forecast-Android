package com.weatherforecast.models;


import com.orm.SugarRecord;

public class Weather extends SugarRecord {

    String type;
    String temp;
    String humidity;
    String pressure;
    String tempMin;
    String tempMax;
    String windSpeed;
    String windDeg;
    String cloudsAll;
    String dt;
    Long cityId;
    String cityName;
    String dtTxt;
    String lon;
    String lat;
    String name;
    String weatherMain;
    String weatherDescription;
    String iconId;
    String currdate;

    public Weather(String weatherDescription, String lon, String lat, String name, String weatherMain, String iconId, String temp, String humidity, String pressure, String tempMin, String tempMax, String windSpeed, String windDeg, String dt, String cloudsAll, Long cityId, String cityName, String type, String currDate) {
        this.weatherDescription = weatherDescription;
        this.lon = lon;
        this.lat = lat;
        this.name = name;
        this.weatherMain = weatherMain;
        this.iconId = iconId;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.dt = dt;
        this.cloudsAll = cloudsAll;
        this.cityId = cityId;
        this.cityName = cityName;
        this.type = type;
        this.currdate = currDate;
    }

    public Weather(String name, String lon, String lat) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    public Weather(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Weather() {
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCloudsAll() {
        return cloudsAll;
    }

    public void setCloudsAll(String cloudsAll) {
        this.cloudsAll = cloudsAll;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    @Override
    public String toString() {
        return name + " " + lon + " - " + lat;
    }

}
