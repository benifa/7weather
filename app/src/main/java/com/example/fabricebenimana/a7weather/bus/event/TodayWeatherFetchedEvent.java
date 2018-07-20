package com.example.fabricebenimana.a7weather.bus.event;

import com.example.fabricebenimana.a7weather.models.ListItem;


public class TodayWeatherFetchedEvent extends BaseEvent {
    private final ListItem weatherInfo;

    public TodayWeatherFetchedEvent(ListItem body, boolean successful) {
        this.weatherInfo = body;
    }

    public ListItem getWeatherInfo() {
        return weatherInfo;
    }
}
