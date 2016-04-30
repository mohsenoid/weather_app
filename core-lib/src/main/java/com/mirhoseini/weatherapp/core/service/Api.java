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
    Observable<WeatherCurrent> getWeather(@Query("q") String city, @Query("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("weather")
    Observable<WeatherCurrent> getWeather(@Query("lat") long lat, @Query("lon") long lon, @Query("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/forecast?q=Tehran&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("forecast")
    Observable<WeatherForecast> getWeatherForecast(@Query("q") String city, @Query("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("forecast")
    Observable<WeatherForecast> getWeatherForecast(@Query("lat") long lat, @Query("lon") long lon, @Query("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/history/city?q=Tehran&type=day&start=1461752800&end=1461952800&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("history")
    Observable<WeatherHistory> getWeatherHistory(@Query("q") String city, @Query("type") String type, @Query("start") long start, @Query("end") long end, @Query("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/history/city?lat={lat}&lon={lon}&type=day&start=1461752800&end=1461952800&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("history")
    Observable<WeatherHistory> getWeatherHistory(@Query("lat") long lat, @Query("lon") long lon, @Query("type") String type, @Query("start") long start, @Query("end") long end, @Query("APPID") String apiKey);

}
