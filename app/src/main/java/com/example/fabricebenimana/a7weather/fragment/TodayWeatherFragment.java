package com.example.fabricebenimana.a7weather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fabricebenimana.a7weather.bus.event.LocationObtainedEvent;
import com.example.fabricebenimana.a7weather.R;
import com.example.fabricebenimana.a7weather.bus.event.TodayWeatherFetchedEvent;
import com.example.fabricebenimana.a7weather.models.ListItem;
import com.example.fabricebenimana.a7weather.models.Main;
import com.example.fabricebenimana.a7weather.service.current.TodayWeatherController;
import com.example.fabricebenimana.a7weather.utils.ForecastViewHelper;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayWeatherFragment extends BusListenerFragment {

    public static final String TAB_TITLE = "Today";

    @BindView(R.id.temperature)
    protected TextView mCurrentTemperature;
    @BindView(R.id.description)
    protected TextView mDescription;
    @BindView(R.id.min_temp)
    protected TextView mMinTemp;
    @BindView(R.id.max_temp)
    protected TextView mMaxTemp;
    @BindView(R.id.pressure)
    protected TextView mPressure;
    @BindView(R.id.wind)
    protected TextView mWind;
    @BindView(R.id.humidity)
    protected TextView mHumidity;
    @BindView(R.id.status)
    protected TextView mWeatherStatus;

    public static TodayWeatherFragment getInstance() {
        return new TodayWeatherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.current_weather_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCurrentLocationAvailable()) {
            new TodayWeatherController().getForecasts();
        }
    }

    @Subscribe
    public void onEvent(LocationObtainedEvent event) {
        new TodayWeatherController().getForecasts();

    }

    @Subscribe
    public void onEvent(TodayWeatherFetchedEvent event) {
        ListItem weatherInfo = event.getWeatherInfo();
        final Main main = weatherInfo.getMain();
        mCurrentTemperature.setText(String.format(getString(R.string.temperature_formatter), main.getTemp()));
        mDescription.setText(weatherInfo.getWeather().get(0).getDescription());
        mMinTemp.setText(String.format(getString(R.string.min_temperature_formatter), main.getTempMin()));
        mMaxTemp.setText(String.format(getString(R.string.max_temperature_formatter), main.getTempMax()));
        mPressure.setText(String.format(getString(R.string.temperature_pressure), main.getPressure()));
        mHumidity.setText(String.format(getString(R.string.temperature_humidity), main.getHumidity()));
        mWind.setText(String.format(getString(R.string.temperature_wind), weatherInfo.getWind().getSpeed()));
        mWeatherStatus.setText(ForecastViewHelper.buildWeatherStatus(getResources(), weatherInfo));
    }

}
