package com.example.fabricebenimana.a7weather.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.fabricebenimana.a7weather.bus.Bus;
import com.example.fabricebenimana.a7weather.SevenElevenWeather;
import com.example.fabricebenimana.a7weather.service.location.LocationController;

import butterknife.ButterKnife;
import butterknife.Unbinder;

abstract class BusListenerFragment extends Fragment {
    protected Unbinder mUnbinder;

    @Override
    public void onStart() {
        super.onStart();
        Bus.register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        Bus.unregister(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }


    protected boolean isCurrentLocationAvailable() {
        final boolean hasLocation = SevenElevenWeather.getInstance().getCurrentLocation() != null;
        if (!hasLocation) {
            new LocationController(getActivity()).requestLocation(true);
        }
        return hasLocation;
    }
}
