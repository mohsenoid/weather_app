package com.mirhoseini.weatherapp.network;

import com.mirhoseini.weatherapp.network.model.WeatherCurrent;
import com.mirhoseini.weatherapp.network.model.WeatherForecast;
import com.mirhoseini.weatherapp.network.model.WeatherHistory;

import retrofit2.http.Field;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface Api {

    // http://api.openweathermap.org/data/2.5/weather?q=Tehran&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("weather")
    Observable<WeatherCurrent> getWeather(@Field("q") String city, @Field("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("weather")
    Observable<WeatherCurrent> getWeather(@Field("lat") long lat, @Field("lon") long lon, @Field("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/forecast?q=Tehran&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("forecast")
    Observable<WeatherForecast> getWeatherForecast(@Field("q") String city, @Field("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("forecast")
    Observable<WeatherForecast> getWeatherForecast(@Field("lat") long lat, @Field("lon") long lon, @Field("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/history/city?q=Tehran&type=day&start=1461752800&end=1461952800&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("history")
    Observable<WeatherHistory> getWeatherHistory(@Field("q") String city, @Field("type") String type, @Field("start") long start, @Field("end") long end, @Field("APPID") String apiKey);

    // http://api.openweathermap.org/data/2.5/history/city?lat={lat}&lon={lon}&type=day&start=1461752800&end=1461952800&APPID=ee498803643d25e7077f98d4d9849f5c
    @GET("history")
    Observable<WeatherHistory> getWeatherHistory(@Field("lat") long lat, @Field("lon") long lon, @Field("type") String type, @Field("start") long start, @Field("end") long end, @Field("APPID") String apiKey);

}
