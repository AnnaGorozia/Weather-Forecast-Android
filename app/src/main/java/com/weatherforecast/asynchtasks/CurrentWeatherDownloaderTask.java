package com.weatherforecast.asynchtasks;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.weatherforecast.models.Weather;
import com.weatherforecast.transport.NetworkEventListener;


public abstract class CurrentWeatherDownloaderTask extends AsyncTask<Void, Void, ArrayList<Weather>>{
	private NetworkEventListener networkEventListener;

	public void setNetworkEventListener(NetworkEventListener networkEventListener) {
		this.networkEventListener = networkEventListener;
	}
	
	@Override
	protected void onPostExecute(ArrayList<Weather> weathers) {
		super.onPostExecute(weathers);
		networkEventListener.onCurrentWeatherDownloaded(weathers);
	}
}
