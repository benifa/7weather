package com.example.fabricebenimana.a7weather.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.fabricebenimana.a7weather.R;
import com.example.fabricebenimana.a7weather.adapter.HomeViewPagerAdapter;
import com.example.fabricebenimana.a7weather.bus.event.LocationObtainedEvent;
import com.example.fabricebenimana.a7weather.service.forecast.ForecastController;
import com.example.fabricebenimana.a7weather.service.location.LocationController;

import org.greenrobot.eventbus.Subscribe;

public class HomeActivity extends BusListenerActivity {

    ViewPager mViewPager;
    private LocationController mLocationController;
    private HomeViewPagerAdapter mHomeViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHomeViewPagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mHomeViewPagerAdapter);

    }

    @Subscribe
    public void onEvent(LocationObtainedEvent event) {
        new ForecastController().getForecasts();
    }


    @Override
    protected void onResume() {
        super.onResume();
        findLocation();
    }

    private void findLocation() {
        mLocationController = new LocationController(this);
        mLocationController.requestLocation(true);
    }
}
