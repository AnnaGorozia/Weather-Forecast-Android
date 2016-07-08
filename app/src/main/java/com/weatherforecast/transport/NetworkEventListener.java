package com.weatherforecast.transport;

import com.weatherforecast.models.Weather;

import java.util.List;

public interface NetworkEventListener {
	public void onCurrentWeatherDownloaded(List<Weather> weathers);
	public void onFiveDayWeatherDownloaded(List<Weather> weathers);
	public void onError(int errorCode, String errorMsg);
}
