package com.weatherforecast.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.weatherforecast.MainActivity;
import com.weatherforecast.R;
import com.weatherforecast.adapters.CitiesAdapter;
import com.weatherforecast.models.Weather;
import com.weatherforecast.transport.NetworkEventListener;

import java.util.ArrayList;
import java.util.List;

/*
* Cities
*/
public class CityTab extends Fragment {

    private ListView list;
    private CitiesAdapter adapter;
    private EditText etSearch;
    private static ArrayList<Weather> weatherList;

    private List<Weather> allWeathers;

    public static CityTab newInstance() {
        return new CityTab();
    }

    public CityTab() {

    }

    @Override
    public void onResume(){
        super.onResume();
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null) {
                    weatherList.clear();
                    weatherList.addAll(allWeathers);
                    adapter.notifyDataSetChanged();
                } else {
                    ArrayList<Weather> filtered = new ArrayList<Weather>();
                    for (Weather w : allWeathers) {
                        if (w.getName().toLowerCase().startsWith(s.toString().toLowerCase())) {
                            filtered.add(w);
                        }
                    }
                    weatherList.clear();
                    weatherList.addAll(filtered);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherList = new ArrayList<>();
        initApp();
        list = (ListView) getView().findViewById(R.id.cityListView);
        list.requestFocus();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Weather weather = (Weather) list.getItemAtPosition(position);
                if (weather != null) {
                    MainActivity.setCity(weather.getCityName());
                    MainActivity.switchToCurrentWeather();
                    Toast.makeText(MainActivity.getContext(), "City set to " + weather.getCityName(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        refreshWeatherList();
        etSearch = (EditText) getView().findViewById(R.id.inputSearch);
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null) {
                    list.setAdapter(null);
                    adapter.clear();
                    adapter.addAll(allWeathers);
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                } else {
                    ArrayList<Weather> filtered = new ArrayList<Weather>();
                    for (Weather w : allWeathers) {
                        if (w.getName().toLowerCase().startsWith(s.toString().toLowerCase())) {
                            filtered.add(w);
                        }
                    }
                    list.setAdapter(null);
                    adapter.clear();
                    adapter.addAll(filtered);
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initApp() {
    }

    private void refreshWeatherList() {
        weatherList = (ArrayList<Weather>) Weather.find(Weather.class, "type = ?", "curr");
        if (weatherList != null) {
            if (adapter == null) {
                adapter = new CitiesAdapter(this.getActivity(), weatherList);
                list.setAdapter(adapter);
            } else {
                list.setAdapter(null);
                adapter.clear();
                adapter.addAll(weatherList);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }
        }
        allWeathers = new ArrayList<>();
        allWeathers.addAll(weatherList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment4, container, false);

    }

}