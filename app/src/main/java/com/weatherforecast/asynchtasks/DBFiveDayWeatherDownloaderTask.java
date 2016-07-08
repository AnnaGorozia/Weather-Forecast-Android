package com.weatherforecast.asynchtasks;

import com.orm.SugarRecord;
import com.weatherforecast.models.Weather;

import java.util.ArrayList;

public class DBFiveDayWeatherDownloaderTask extends CurrentWeatherDownloaderTask {
    @Override
    protected ArrayList<Weather> doInBackground(Void... params) {
        return (ArrayList<Weather>) SugarRecord.listAll(Weather.class);
    }

}