package com.example.fabricebenimana.a7weather.bus;

import android.location.Location;

import com.example.fabricebenimana.a7weather.bus.event.ForecastsFetchedEvent;
import com.example.fabricebenimana.a7weather.models.ForecastsContainer;
import com.example.fabricebenimana.a7weather.models.ListItem;
import com.example.fabricebenimana.a7weather.bus.event.BaseEvent;
import com.example.fabricebenimana.a7weather.bus.event.LocationObtainedEvent;
import com.example.fabricebenimana.a7weather.bus.event.TodayWeatherFetchedEvent;

import org.greenrobot.eventbus.EventBus;

public class Bus {

    private static final int MAX_RANDOM_INT = 15;

    private Bus() {}

    public static void register(Object object) {
        EventBus.getDefault().register(object);
    }

    public static void unregister(Object object) {
        EventBus.getDefault().unregister(object);
    }

    private static void post(BaseEvent event) {
        EventBus.getDefault().post(event);
    }

    private static void postSticky(BaseEvent event) {
        EventBus.getDefault().postSticky(event);
    }

    private static BaseEvent getStickyEvent(final Class clazz) {
        final Object event = EventBus.getDefault().getStickyEvent(clazz);
        if (event != null && event instanceof BaseEvent) {
            return (BaseEvent) event;
        }
        return null;
    }

    public static boolean isStickyEventPosted(final Class clazz) {
        return getStickyEvent(clazz) != null;
    }

    public static void removeStickyEvent(final Class clazz) {
        final BaseEvent event = getStickyEvent(clazz);
        if (event != null) {
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    public static void onForecastsFetched(ForecastsContainer body, boolean successful) {
        post(new ForecastsFetchedEvent(body, successful));
    }

    public static void onLocationObtained(Location location) {
        post(new LocationObtainedEvent(location));
    }

    public static void onTodayWeatherFetched(ListItem body, boolean successful) {
        post(new TodayWeatherFetchedEvent(body, successful));
    }
}
