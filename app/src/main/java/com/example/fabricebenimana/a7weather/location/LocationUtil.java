package com.example.fabricebenimana.a7weather.location;

import android.location.Location;
import android.support.annotation.NonNull;
import com.google.android.gms.maps.model.LatLng;

public class LocationUtil {

    private LocationUtil() {

    }

    public static LatLng getLatLng(@NonNull final Location location) {

        return new LatLng(location.getLatitude(), location.getLongitude());
    }

}
