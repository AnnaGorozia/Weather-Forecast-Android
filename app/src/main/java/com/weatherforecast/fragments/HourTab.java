package com.weatherforecast.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.weatherforecast.MainActivity;
import com.weatherforecast.R;
import com.weatherforecast.adapters.HourForecastAdapter;
import com.weatherforecast.models.Weather;
import com.weatherforecast.transport.NetworkEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Hourly
 */

public class HourTab extends Fragment {

    private ListView list;
    private HourForecastAdapter adapter;
    private static ArrayList<Weather> weatherList;

    public static HourTab newInstance() {
        return new HourTab();
    }

    public HourTab() {

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherList = new ArrayList<>();
        initApp();
        list = (ListView) getView().findViewById(R.id.hourlyListView);

        refreshWeatherList();
    }

    private void initApp() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    private void refreshWeatherList() {
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String formattedDate = df2.format(date);
        weatherList = (ArrayList<Weather>) Weather.find(Weather.class, "name = ? and type = ? and currdate = ?",
                MainActivity.getCity(), "five", formattedDate);
        if (weatherList != null) {
            if (adapter == null) {
                adapter = new HourForecastAdapter(this.getActivity(), weatherList);
                list.setAdapter(adapter);
            } else {
                list.setAdapter(null);
                adapter.clear();
                adapter.addAll(weatherList);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }
        }
    }
}