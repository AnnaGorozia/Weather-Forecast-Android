package com.weatherforecast;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.weatherforecast.fragments.CurrentTab;
import com.weatherforecast.fragments.HourTab;
import com.weatherforecast.fragments.DailyTab;
import com.weatherforecast.fragments.CityTab;
import com.weatherforecast.fragments.MapTab;
import com.weatherforecast.fragments.NotificationTab;
import com.weatherforecast.models.City;
import com.weatherforecast.models.Notification;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();
    private MyPagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    private static Context context;

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        MainActivity.city = city;
    }
    private static ViewPager viewPager;
    public static String city;
    public static ArrayList<City> getCities() {
        return cities;
    }

    private static ArrayList<City> cities = new ArrayList<>();

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        fillCitiesArray();

        city = "Tbilisi";
        fragmentList.add(CurrentTab.newInstance());
        fragmentList.add(HourTab.newInstance());
        fragmentList.add(DailyTab.newInstance());
        fragmentList.add(CityTab.newInstance());
        fragmentList.add(MapTab.newInstance());
        fragmentList.add(NotificationTab.newInstance());

        tabTitles.add("Now");
        tabTitles.add("Hourly");
        tabTitles.add("Daily");
        tabTitles.add("Cities");
        tabTitles.add("Map");
        tabTitles.add("Notifications");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void fillCitiesArray() {
        cities.add(new City(611583, "Tqvarcheli", "42.840351", "41.680069", "GE"));
        cities.add(new City(611694, "Telavi", "41.919781", "45.473148", "GE"));
        cities.add(new City(611717, "Tbilisi", "41.694111", "44.833679", "GE"));
        cities.add(new City(612287, "Rustavi", "41.549492",	"44.993229", "GE"));
        cities.add(new City(612366, "Poti", "42.146160", "41.671970", "GE"));
        cities.add(new City(612652, "Ochamchire", "42.712318", "41.468632", "GE"));
        cities.add(new City(613762, "Kobuleti", "41.820000", "41.775280", "GE"));
        cities.add(new City(614455, "Gori", "41.984219", "44.115780", "GE"));
        cities.add(new City(615860, "Akhaltsikhe", "41.639011", "42.982620", "GE"));
        cities.add(new City(824288, "Tsqaltubo", "42.341290", "42.597599", "GE"));
        cities.add(new City(6324644, "Meria", "41.692780", "44.801540", "GE"));
    }

    public static void switchToCurrentWeather(){
        viewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return fragmentList.get(pos);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }
}
