package com.example.fabricebenimana.a7weather.utils;

import android.content.res.Resources;

import com.example.fabricebenimana.a7weather.R;
import com.example.fabricebenimana.a7weather.models.ListItem;

public final class ForecastViewHelper {

    public static String buildWeatherStatus(Resources resources, ListItem forecast) {
        String status = resources.getString(R.string.clear);
        switch (forecast.getWeather().get(0).getMain()) {
            case "Rain":
                status =  resources.getString(R.string.rain);
                break;
            case "Clouds" :
                status =  resources.getString(R.string.cloud);
                break;
            default:
            case "Clear":
                resources.getString(R.string.clear);
        }
        return status;
    }
}
