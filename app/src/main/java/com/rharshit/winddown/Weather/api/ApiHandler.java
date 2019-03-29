package com.rharshit.winddown.Weather.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHandler {

    @GET("/api/location/search/")
    Call<List<City>> getCities(@Query("query") String query);

    @GET("api/location/{woeid}/")
    Call<WeatherInfo> getWeather(@Path("woeid") int woeid);
}
