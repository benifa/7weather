package com.example.fabricebenimana.a7weather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fabricebenimana.a7weather.R;
import com.example.fabricebenimana.a7weather.adapter.ForecastAdapter;
import com.example.fabricebenimana.a7weather.bus.event.ForecastsFetchedEvent;
import com.example.fabricebenimana.a7weather.bus.event.LocationObtainedEvent;
import com.example.fabricebenimana.a7weather.service.forecast.ForecastController;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

public class ForecastWeatherFragment extends BusListenerFragment {

    public static final String TAB_TITLE = "Forecast";
    @BindView(R.id.forecast_list)
    protected RecyclerView mForecastRecyclerView;

    public static ForecastWeatherFragment getInstance() {
        return new ForecastWeatherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(
                R.layout.history_forecast_fragment, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCurrentLocationAvailable()) {
            new ForecastController().getForecasts();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mForecastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Subscribe
    public void onEvent(LocationObtainedEvent event) {
        new ForecastController().getForecasts();

    }

    @Subscribe
    public void onEvent(ForecastsFetchedEvent event) {
        List forecastsList = event.getForecastsList();
        ForecastAdapter forecastAdapter = new ForecastAdapter(forecastsList);
        mForecastRecyclerView.setAdapter(forecastAdapter);

    }
}
