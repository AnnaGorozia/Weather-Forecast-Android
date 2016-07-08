package com.weatherforecast.asynchtasks;


import com.weatherforecast.MainActivity;
import com.weatherforecast.models.City;
import com.weatherforecast.models.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class URLFiveDayWeatherDownloaderTask extends FiveDayWeatherDownloaderTask {
    private ArrayList<Weather> weathers;
    @Override
    protected ArrayList<Weather> doInBackground(Void... params){
        InputStream inStream = null;
        ArrayList<Weather> weatherList = new ArrayList<>();
        for (City city : MainActivity.getCities()){
            URLFileWorker jsonFileWorker = new URLFileWorker("http://api.openweathermap.org/data/2.5/forecast?id=" + city.getCode() + "&units=metric&APPID=31d577c92cea77170efc2814b9efcc65");
            String json = jsonFileWorker.getJsonFileString();
            if (json.length() != 0) {
                JSONObject jObj = jsonFileWorker.getJsonObject(json);
                ArrayList<Weather> weathers = null;
                try {
                    weathers = jsonFileWorker.jsonFiveDayFileParser(jObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (weathers != null) {
                    for (Weather w : weathers) {
                        weatherList.add(w);
                    }
                }
            }

        }

        this.weathers = weatherList;
        return weatherList;
    }
}
