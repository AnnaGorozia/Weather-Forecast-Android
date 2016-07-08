package com.weatherforecast.asynchtasks;

import android.os.AsyncTask;

import com.weatherforecast.models.Weather;
import com.weatherforecast.transport.NetworkEventListener;

import java.util.ArrayList;

public abstract class FiveDayWeatherDownloaderTask extends AsyncTask<Void, Void, ArrayList<Weather>> {
    private NetworkEventListener networkEventListener;

    public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        networkEventListener.onFiveDayWeatherDownloaded(weathers);
    }
}
