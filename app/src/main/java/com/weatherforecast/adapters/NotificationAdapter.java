package com.weatherforecast.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weatherforecast.R;
import com.weatherforecast.models.Notification;

import java.util.ArrayList;


public class NotificationAdapter extends ArrayAdapter<Notification> {

    private final Activity context;
    private final ArrayList<Notification> notificationArrayList;

    public NotificationAdapter(Activity context, ArrayList<Notification> notificationArrayList) {
        super(context, R.layout.raw_layout_notification, notificationArrayList);
        this.notificationArrayList = notificationArrayList;
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
            rowView = inflater.inflate(R.layout.raw_layout_notification, null);
            NotificationViewHolder viewHolder = new NotificationViewHolder();

            if (rowView != null) {
                viewHolder.date = (TextView) rowView.findViewById(R.id.notificationDate);
                viewHolder.city = (TextView) rowView.findViewById(R.id.notificationCity);

                rowView.setTag(viewHolder);
            }
        }

        if (rowView != null) {
            final NotificationViewHolder holder = (NotificationViewHolder) rowView.getTag();
            final Notification notification = notificationArrayList.get(position);
            if (notification != null) {
                holder.date.setText(String.valueOf(String.valueOf(notification.getDate())));
                holder.city.setText(String.valueOf(notification.getName()));
            }
        }

        return rowView;
    }

    @Override
    public long getItemId(int position) {
        Notification item = getItem(position);
        return item.getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    static class NotificationViewHolder {
        public TextView city;
        public TextView date;
    }

}
