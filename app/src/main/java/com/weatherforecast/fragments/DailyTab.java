package com.weatherforecast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.weatherforecast.MainActivity;
import com.weatherforecast.R;
import com.weatherforecast.adapters.DailyForecastAdapter;
import com.weatherforecast.models.Weather;
import com.weatherforecast.transport.NetworkEventListener;

import java.util.ArrayList;
import java.util.List;

/*
 * Daily
 */


public class DailyTab extends Fragment {

    private ListView list;
    private DailyForecastAdapter adapter;
    private static ArrayList<Weather> weatherList;

    public static DailyTab newInstance() {
        return new DailyTab();
    }

    public DailyTab() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment3, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherList = new ArrayList<>();
        initApp();
        list = (ListView) getView().findViewById(R.id.dailyListView);

        refreshWeatherList();
    }

    private void initApp() {
    }


    private void refreshWeatherList() {
        weatherList = (ArrayList<Weather>) Weather.findWithQuery(Weather.class, "SELECT * FROM Weather WHERE name = ? and type = ? GROUP BY currdate", MainActivity.getCity(), "five");
        if (weatherList != null) {
            if (adapter == null) {
                adapter = new DailyForecastAdapter(this.getActivity(), weatherList);
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