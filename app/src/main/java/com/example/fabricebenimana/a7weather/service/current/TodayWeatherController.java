package com.example.fabricebenimana.a7weather.service.current;

import com.example.fabricebenimana.a7weather.bus.Bus;
import com.example.fabricebenimana.a7weather.SevenElevenWeather;
import com.example.fabricebenimana.a7weather.models.ListItem;
import com.example.fabricebenimana.a7weather.service.base.ApiClient;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayWeatherController {

    private static final String OPEN_WEATHER_MAP_BASE_URL = "http://api.openweathermap.org/";
    private TodayWeatherService currentWeatherService;

    public TodayWeatherController() {
        this.currentWeatherService = ApiClient.getInstance(OPEN_WEATHER_MAP_BASE_URL).create(TodayWeatherService.class);
    }

    public void getForecasts() {

        LatLng currentLocation = SevenElevenWeather.getInstance().getCurrentLocation();
        currentWeatherService.getCurrentWeather(String.valueOf(currentLocation.latitude),
                String.valueOf(currentLocation.longitude), SevenElevenWeather.GOOGLE_MAPS_KEY).enqueue(new Callback<ListItem>() {
            @Override
            public void onResponse(Call<ListItem> call, Response<ListItem> response) {
                Bus.onTodayWeatherFetched(response.body(), response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ListItem> call, Throwable t) {

            }
        });
    }
}
