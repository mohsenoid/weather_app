package com.mirhoseini.weatherapp.core.service;


import com.mirhoseini.weatherapp.core.service.model.WeatherCurrent;
import com.mirhoseini.weatherapp.core.service.model.WeatherForecast;
import com.mirhoseini.weatherapp.core.service.model.WeatherHistory;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface Api {

    // http://api.openweathermap.org/data/2.5/weather?q=Tehran&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("weather")
    Observable<WeatherCurrent> getWeather(@Query("q") String city, @Query("APPID") String apiKey, @Query("units") String units);

    // http://api.openweathermap.org/data/2.5/forecast?q=Tehran&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("forecast/daily")
    Observable<WeatherForecast> getWeatherForecast(@Query("q") String city, @Query("APPID") String apiKey, @Query("units") String units, @Query("cnt") int count);

    //http://api.openweathermap.org/data/2.5/history/city?q=Berlin&type=hour&start=1461484800&end=1456819200&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("history")
    Observable<WeatherHistory> getWeatherHistory(@Query("q") String city, @Query("type") String type, @Query("start") long start, @Query("end") long end, @Query("APPID") String apiKey, @Query("units") String units);

}
