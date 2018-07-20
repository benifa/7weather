package com.example.fabricebenimana.a7weather.service.forecast;

import com.example.fabricebenimana.a7weather.models.ForecastsContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastService {
    @GET("/data/2.5/forecast")
    Call<ForecastsContainer> getForecasts(@Query("lat") String lat,
                                          @Query("lon") String longi,
                                          @Query("APPID") String apId);
}
