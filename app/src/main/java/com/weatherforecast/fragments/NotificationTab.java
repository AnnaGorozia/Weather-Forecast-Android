package com.weatherforecast.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.weatherforecast.NotificationSetter;
import com.weatherforecast.R;
import com.weatherforecast.adapters.NotificationAdapter;
import com.weatherforecast.models.Notification;
import com.weatherforecast.models.Weather;

import java.util.ArrayList;

/*
 * Daily
 */


public class NotificationTab extends Fragment {

    private ListView list;
    private ImageView add;
    private NotificationAdapter adapter;
    private static ArrayList<Notification> notificationList;

    public static NotificationTab newInstance() {
        return new NotificationTab();
    }

    public NotificationTab() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment6, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notificationList = new ArrayList<>();
        initApp();
        add = (ImageView) getView().findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationTab.this.getContext(), NotificationSetter.class);
                startActivityForResult(intent, 1);
            }
        });
        list = (ListView) getView().findViewById(R.id.notificationListView);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notification = (Notification) list.getItemAtPosition(position);
                if (notification != null) {
                    notification.delete();
                    refreshNotificationList();
                }
                return false;
            }
        });
        refreshNotificationList();
    }

    private void initApp() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshNotificationList();
    }

    private void refreshNotificationList() {
        notificationList = (ArrayList<Notification>) Notification.listAll(Notification.class);
        if (notificationList != null) {
            if (adapter == null) {
                adapter = new NotificationAdapter(this.getActivity(), notificationList);
                list.setAdapter(adapter);
            } else {
                list.setAdapter(null);
                adapter.clear();
                adapter.addAll(notificationList);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }
        }
    }
}