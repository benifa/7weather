package com.example.fabricebenimana.a7weather.service.forecast;

import com.example.fabricebenimana.a7weather.bus.Bus;
import com.example.fabricebenimana.a7weather.SevenElevenWeather;
import com.example.fabricebenimana.a7weather.models.ForecastsContainer;
import com.example.fabricebenimana.a7weather.service.base.ApiClient;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastController {

    private static final String OPEN_WEATHER_MAP_BASE_URL = "http://api.openweathermap.org/";
    private ForecastService restaurantService;

    public ForecastController() {
        this.restaurantService = ApiClient.getInstance(OPEN_WEATHER_MAP_BASE_URL).create(ForecastService.class);
    }

    public void getForecasts() {

        LatLng currentLocation = SevenElevenWeather.getInstance().getCurrentLocation();
        restaurantService.getForecasts(String.valueOf(currentLocation.latitude), String.valueOf(currentLocation.longitude),
                SevenElevenWeather.GOOGLE_MAPS_KEY).enqueue(new Callback<ForecastsContainer>() {
            @Override
            public void onResponse(Call<ForecastsContainer> call, Response<ForecastsContainer> response) {
                Bus.onForecastsFetched(response.body(), response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ForecastsContainer> call, Throwable t) {
                Bus.onForecastsFetched(null, false);

            }
        });
    }
}
