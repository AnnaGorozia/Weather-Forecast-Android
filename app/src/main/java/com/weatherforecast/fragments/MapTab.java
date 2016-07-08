package com.weatherforecast.fragments;


import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.weatherforecast.MainActivity;
import com.weatherforecast.R;
import com.weatherforecast.models.Weather;

import java.util.List;

public class MapTab extends Fragment  implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static MapTab newInstance() {
        return new MapTab();
    }

    private GoogleMap mMap;

    public MapTab() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment5, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        List<Weather> weathers = Weather.find(Weather.class, "type = ?", "curr");
        Resources res = this.getActivity().getResources();
        Drawable drawable;

        for (Weather weather : weathers) {
            LatLng curr = new LatLng(Double.parseDouble(weather.getLat()), Double.parseDouble(weather.getLon()));
            drawable = res.getDrawable(MainActivity.getContext().getResources().getIdentifier("a" + weather.getIconId() , "drawable", MainActivity.getContext().getPackageName()));
             mMap.addMarker(new MarkerOptions().position(curr)
                    .icon(BitmapDescriptorFactory.fromBitmap(((BitmapDrawable)drawable).getBitmap()))
                    .snippet(weather.getCityName())
                    .title(weather.getWeatherDescription()
                    + " "
                    + weather.getTemp() + " \u2103"));
            if (weather.getName().toLowerCase().equals("tbilisi")) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(curr));
            }
        }
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 6 ) );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        MainActivity.setCity(marker.getSnippet());
        MainActivity.switchToCurrentWeather();
        Toast.makeText(MainActivity.getContext(), "City set to " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
        return true;
    }
}