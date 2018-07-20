package com.example.fabricebenimana.a7weather;

import android.app.Application;

import com.example.fabricebenimana.a7weather.utils.Prefs;
import com.google.android.gms.maps.model.LatLng;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;

public class SevenElevenWeather extends Application {
    public static final String GOOGLE_MAPS_KEY = "018f553bfbc450d20bf8ab946852e636";

    private static SevenElevenWeather instance;

    private LatLng currentLocation;

    public static SevenElevenWeather getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Prefs.init(this);
        Iconify.with(new MaterialModule())
                .with(new FontAwesomeModule());
    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }
}
