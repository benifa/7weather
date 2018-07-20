package com.example.fabricebenimana.a7weather.activity;

import android.support.v4.app.FragmentActivity;

import com.example.fabricebenimana.a7weather.bus.Bus;

abstract class BusListenerActivity extends FragmentActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Bus.unregister(this);
    }
}