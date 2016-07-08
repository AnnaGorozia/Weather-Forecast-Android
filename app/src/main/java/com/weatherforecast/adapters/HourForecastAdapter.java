package com.weatherforecast.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherforecast.MainActivity;
import com.weatherforecast.R;
import com.weatherforecast.models.Weather;

import java.util.ArrayList;


public class HourForecastAdapter extends ArrayAdapter<Weather> {

    private final Activity context;
    private final ArrayList<Weather> weathers;

    public HourForecastAdapter(Activity context, ArrayList<Weather> weathers) {
        super(context, R.layout.raw_layout_hourly, weathers);
        this.weathers = weathers;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.raw_layout_hourly, null);
            WeatherViewHolder viewHolder = new WeatherViewHolder();

            if (rowView != null) {
                viewHolder.date = (TextView) rowView.findViewById(R.id.hourlyDate);
                viewHolder.weatherDesc = (TextView) rowView.findViewById(R.id.hourlyDescription);
                viewHolder.listIcon = (ImageView) rowView.findViewById(R.id.hourlyIcon);
                viewHolder.weatherTemp = (TextView) rowView.findViewById(R.id.hourlyTemperature);

                rowView.setTag(viewHolder);
            }
        }

        if (rowView != null) {
            final WeatherViewHolder holder = (WeatherViewHolder) rowView.getTag();
            final Weather weather = weathers.get(position);
            if (weather != null) {
                holder.date.setText(String.valueOf(String.valueOf(weather.getDtTxt().split("\\ ")[1])));
                holder.weatherDesc.setText(String.valueOf(weather.getWeatherDescription()));
                holder.weatherTemp.setText(weather.getTemp()  + " \u2103");
                holder.listIcon.setImageResource(MainActivity.getContext().getResources().getIdentifier("a" + weather.getIconId() , "drawable", MainActivity.getContext().getPackageName()));
            }
        }

        return rowView;
    }

    @Override
    public long getItemId(int position) {
        Weather item = getItem(position);
        return item.getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    static class WeatherViewHolder {
        public TextView date;
        public ImageView listIcon;
        public TextView weatherDesc;
        public TextView weatherTemp;
    }

}
