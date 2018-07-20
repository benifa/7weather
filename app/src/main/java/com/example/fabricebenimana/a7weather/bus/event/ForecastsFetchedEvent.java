package com.example.fabricebenimana.a7weather.bus.event;
import com.example.fabricebenimana.a7weather.models.ForecastsContainer;


import java.util.List;

public final class ForecastsFetchedEvent extends NetworkEvent {
    List forecasts;
    public ForecastsFetchedEvent(ForecastsContainer body, boolean successful) {
        super(body, successful);
        forecasts = body.getList();
    }

    public List getForecastsList() {
        return forecasts;
    }
}
