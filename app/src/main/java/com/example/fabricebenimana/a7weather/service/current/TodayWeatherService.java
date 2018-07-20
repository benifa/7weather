package com.example.fabricebenimana.a7weather.service.current;

import com.example.fabricebenimana.a7weather.models.ListItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TodayWeatherService {
    @GET("/data/2.5/weather")
    Call<ListItem> getCurrentWeather(@Query("lat") String lat,
                                     @Query("lon") String longi, @Query("APPID") String apId);
}
