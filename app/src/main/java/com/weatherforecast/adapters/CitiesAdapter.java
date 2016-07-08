package com.weatherforecast.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weatherforecast.MainActivity;
import com.weatherforecast.R;
import com.weatherforecast.models.Weather;

import java.util.ArrayList;


public class CitiesAdapter extends ArrayAdapter<Weather> {

    private final Activity context;
    private ArrayList<Weather> weathers;
    private ArrayList<Weather> displayWeathers;

    public CitiesAdapter(Activity context, ArrayList<Weather> weathers) {
        super(context, R.layout.raw_layout_weather, weathers);
        this.weathers = weathers;
        this.displayWeathers = weathers;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Weather> FilteredArrList = new ArrayList<Weather>();

                if (displayWeathers == null) {
                    displayWeathers = new ArrayList<Weather>(weathers); // saves the original data in mOriginalValues
                }
                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = displayWeathers.size();
                    results.values = displayWeathers;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < displayWeathers.size(); i++) {
                        String data = displayWeathers.get(i).getName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(displayWeathers.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
//                CitiesAdapter.this.clear();
//                CitiesAdapter.this.notifyDataSetChanged();

                weathers = (ArrayList<Weather>) results.values;
                CitiesAdapter.this.notifyDataSetChanged();

            }
        };
        return filter;
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
            rowView = inflater.inflate(R.layout.raw_layout_weather, null);
            CityViewHolder viewHolder = new CityViewHolder();

            if (rowView != null) {
                viewHolder.listCity = (TextView) rowView.findViewById(R.id.listCity);
                viewHolder.listTemp = (TextView) rowView.findViewById(R.id.listTemp);
                viewHolder.listIcon = (ImageView) rowView.findViewById(R.id.listIcon);
                rowView.setTag(viewHolder);
            }
        }

        if (rowView != null) {
            final CityViewHolder holder = (CityViewHolder) rowView.getTag();
            Weather weather = null;
            if (position < weathers.size()) {
                weather = weathers.get(position);
            }
            if (weather != null) {
                holder.listCity.setText(String.valueOf(weather.getName()));
                holder.listTemp.setText(weather.getTemp()  + " \u2103");
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

    static class CityViewHolder {
        public TextView listTemp;
        public ImageView listIcon;
        public TextView listCity;
    }

}
