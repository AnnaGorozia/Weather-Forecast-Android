package com.weatherforecast.asynchtasks;

import android.util.Log;

import com.weatherforecast.models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class URLFileWorker {

    private String jsonUrl;

    public URLFileWorker(String jsonUrl){
        this.jsonUrl = jsonUrl;
    }

    public ArrayList<Weather> jsonFiveDayFileParser(JSONObject json) throws JSONException {
        ArrayList<Weather> weathers = new ArrayList<>();
        Weather weather = null;

        JSONObject cityObj = json.getJSONObject("city");
        String cityId = cityObj.getString("id");
        String name = cityObj.getString("name");

        JSONObject coordObj = cityObj.getJSONObject("coord");
        String lon = "" + coordObj.getDouble("lon");
        String lat = "" + coordObj.getDouble("lat");

        int cnt = Integer.parseInt(json.getString("cnt"));

        JSONArray list = json.getJSONArray("list");
        for (int i = 0; i < cnt; i++) {
            try {
                JSONObject object = list.getJSONObject(i);
                JSONArray weatherArr = object.getJSONArray("weather");
                JSONObject weatherObj = weatherArr.getJSONObject(0);
                String main = weatherObj.getString("main");
                String description = weatherObj.getString("description");
                String icon = weatherObj.getString("icon");

                JSONObject mainObj = object.getJSONObject("main");
                String temp = mainObj.getString("temp");
                String pressure = mainObj.getString("pressure");
                String humidity = mainObj.getString("humidity");
                String tempMin = mainObj.getString("temp_min");
                String tempMax = mainObj.getString("temp_max");

                String dt = object.getString("dt");

                String dtTxt = object.getString("dt_txt");
                String[] tmp = dtTxt.split("\\ ");
                String formattedDate = tmp[0];
                weather = new Weather(description, lon, lat, name, main, icon, temp, humidity, pressure, tempMin, tempMax, null, null, dt, null, Long.parseLong(cityId), name, "five", formattedDate);
                weather.setDtTxt(dtTxt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            weathers.add(weather);
        }

        return weathers;
    }

    public Weather jsonFileParser(JSONObject json){
        Weather weather = null;
        try {
            JSONObject coordObj = json.getJSONObject("coord");
            String lon = "" + coordObj.getDouble("lon");
            String lat = "" + coordObj.getDouble("lat");

            JSONArray weatherArr = json.getJSONArray("weather");
            JSONObject weatherObj = weatherArr.getJSONObject(0);
            String main = weatherObj.getString("main");
            String description = weatherObj.getString("description");
            String icon = weatherObj.getString("icon");

            JSONObject mainObj = json.getJSONObject("main");
            String temp = mainObj.getString("temp");
            String pressure = mainObj.getString("pressure");
            String humidity = mainObj.getString("humidity");
            String tempMin = mainObj.getString("temp_min");
            String tempMax = mainObj.getString("temp_max");

            JSONObject windObj = json.getJSONObject("wind");
            String speed = windObj.getString("speed");
            String deg = windObj.getString("deg");

            JSONObject cloudsObj = json.getJSONObject("clouds");
            String all = cloudsObj.getString("all");

            String dt = json.getString("dt");
            String cityId = json.getString("id");
            String name = json.getString("name");

            DateFormat df2 = new SimpleDateFormat("MM/dd");
            Date date = new Date();
            String formattedDate = df2.format(date);

            weather = new Weather(description, lon, lat, name, main, icon, temp, humidity, pressure, tempMin, tempMax, speed, deg, dt, all, Long.parseLong(cityId), name, "curr", formattedDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    public InputStream getInputStream(){
        InputStream input = null;
        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.addRequestProperty("x-api-key",
//                    "327f0c4e1cfb6ac8bf357744e83629d7");
            connection.setDoInput(true);
            connection.connect();
            input = connection.getInputStream();

        }catch (Exception e){
            e.printStackTrace();
        }
        return input;
    }

    public JSONObject getJsonObject(String json){
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public String getJsonFileString(){
        String json = "";
        InputStream inStream = getInputStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            inStream.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return json;
        }
        return json;
    }

}
