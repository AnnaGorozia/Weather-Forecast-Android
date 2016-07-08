package com.weatherforecast.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherforecast.MainActivity;
import com.weatherforecast.R;
import com.weatherforecast.asynchtasks.CurrentWeatherDownloaderTask;
import com.weatherforecast.asynchtasks.FiveDayWeatherDownloaderTask;
import com.weatherforecast.asynchtasks.URLCurrentWeatherDownloaderTask;
import com.weatherforecast.asynchtasks.URLFiveDayWeatherDownloaderTask;
import com.weatherforecast.models.Notification;
import com.weatherforecast.models.Weather;
import com.weatherforecast.transport.NetworkEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentTab extends Fragment implements NetworkEventListener {

    private TextView cityField;
    private ImageView iconView;
    private TextView descView;
    private TextView tempView;
    private TextView humidityView;
    private TextView windSpeedView;
    private TextView windDegView;
    private TextView minTempView;
    private TextView maxTempView;

    public static CurrentTab newInstance() {
        return new CurrentTab();
    }

    public CurrentTab() {

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCurrentWeatherParameters();
        checkForAlert();
        if (isNetworkAvailable()) {
            initApp();
        } else {
            Toast.makeText(MainActivity.getContext(), "No Internet Connection. Weather data is not refreshed", Toast.LENGTH_LONG).show();
        }
    }

    private void checkForAlert() {
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String formattedDate = df2.format(date);
        ArrayList<Notification> notifications = (ArrayList<Notification>) Notification.find(Notification.class, "date = ?", formattedDate);
        if (notifications != null) {
            for (Notification n : notifications) {
                Double temp = Double.parseDouble(n.getTemp());
                Double currTemp = 0.0;
                List<Weather> w = Weather.find(Weather.class, "name = ? and type = ?", n.getName(), "curr");
                if (w != null && w.size() > 0) {
                    currTemp = Double.parseDouble(w.get(0).getTemp());
                }
                if (Math.abs(currTemp - temp) > 3) {
                    sendNotification(n.getName(), "" + currTemp);
                }
            }
        }
        Notification.deleteAll(Notification.class, "date = ?", formattedDate);
    }

    private void initApp() {
        CurrentWeatherDownloaderTask currentWeatherDownloaderTask = null;
        currentWeatherDownloaderTask = new URLCurrentWeatherDownloaderTask();
        currentWeatherDownloaderTask.setNetworkEventListener(this);
        currentWeatherDownloaderTask.execute();

        FiveDayWeatherDownloaderTask fiveDayWeatherDownloaderTask = null;
        fiveDayWeatherDownloaderTask = new URLFiveDayWeatherDownloaderTask();
        fiveDayWeatherDownloaderTask.setNetworkEventListener(this);
        fiveDayWeatherDownloaderTask.execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        cityField = (TextView) view.findViewById(R.id.cityName);
        iconView = (ImageView) view.findViewById(R.id.weatherIcon);
        descView = (TextView) view.findViewById(R.id.weatherDesc);
        tempView = (TextView) view.findViewById(R.id.weatherTemp);
        humidityView = (TextView) view.findViewById(R.id.humidity);
        windSpeedView = (TextView) view.findViewById(R.id.windSpeed);
        windDegView = (TextView) view.findViewById(R.id.windDeg);
        minTempView = (TextView) view.findViewById(R.id.minTemp);
        maxTempView = (TextView) view.findViewById(R.id.maxTemp);

        return view;
    }

    @Override
    public void onCurrentWeatherDownloaded(List<Weather> weathers) {
        if (!weathers.isEmpty()) {
            Weather.deleteAll(Weather.class, "type = ?", "curr");
            for (Weather w : weathers) {
                if (w != null)
                    w.save();
            }
//            Weather.saveInTx(weathers);
            setCurrentWeatherParameters();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MainActivity.getContext().getSystemService(MainActivity.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onFiveDayWeatherDownloaded(List<Weather> weathers) {
        if (!weathers.isEmpty()) {
            Weather.deleteAll(Weather.class, "type = ?", "five");
//            Weather.saveInTx(weathers);
            for (Weather w : weathers) {
                if (w != null)
                    w.save();
            }
            System.out.println("--------------------------------------" + weathers.size());
        }
    }

    @Override
    public void onError(int errorCode, String errorMsg) {

    }

    private void setCurrentWeatherParameters() {
        List<Weather> w = Weather.find(Weather.class, "name = ? and type = ?", MainActivity.getCity(), "curr");
        Weather weather = null;
        if (w != null && w.size() > 0) {
            weather = w.get(0);
        }
        if (weather != null) {
            cityField.setText("Current Weather in " + weather.getCityName());
            iconView.setImageResource(MainActivity.getContext().getResources().getIdentifier("a" + weather.getIconId() , "drawable", MainActivity.getContext().getPackageName()));
            descView.setText(weather.getWeatherDescription());
            tempView.setText(weather.getTemp() + " \u2103");
            humidityView.setText("Humidity " + weather.getHumidity() + "%");
            windSpeedView.setText("Wind Speed " + weather.getWindSpeed() + " km/h");
            windDegView.setText("Wind Deg " + weather.getWindDeg());
            minTempView.setText("Min Temperature " + weather.getTempMin() + " \u2103");
            maxTempView.setText("Max Temperature " + weather.getTempMax() + " \u2103");
        }
    }

    private void sendNotification(String name, String temp) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getActivity())
                        .setSmallIcon(R.mipmap.weather)
                        .setContentTitle("Weather changed in " + name)
                        .setContentText("Current temperature is " + temp + " \u2103");

        NotificationManager mNotificationManager =
                (NotificationManager) this.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}