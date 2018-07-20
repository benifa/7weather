package com.example.fabricebenimana.a7weather.bus.event;

import android.location.Location;

import com.example.fabricebenimana.a7weather.bus.event.BaseEvent;

public final class LocationObtainedEvent extends BaseEvent {
    private Location location;
    public LocationObtainedEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
