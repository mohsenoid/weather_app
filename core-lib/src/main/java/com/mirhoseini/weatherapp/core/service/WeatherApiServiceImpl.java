package com.mirhoseini.weatherapp.core.service;


import com.mirhoseini.weatherapp.core.utils.Constants;

import org.openweathermap.model.WeatherHistory;
import org.openweathermap.model.WeatherMix;

import rx.Observable;


/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherApiServiceImpl implements WeatherApiService {

    private final Api api;

    public WeatherApiServiceImpl(Api api) {
        this.api = api;
    }

    @Override
    public Observable<WeatherMix> loadWeather(String city) {

        return Observable.combineLatest(
                api.getWeather(city, Constants.API_KEY, Constants.WEATHER_UNITS),
                api.getWeatherForecast(city, Constants.API_KEY, Constants.WEATHER_UNITS, Constants.FORECAST_DAY_COUNT),
                (weatherCurrent, weatherForecast) -> new WeatherMix(weatherCurrent, weatherForecast));
    }

    @Override
    public Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end) {

        return api.getWeatherHistory(city, Constants.HISTORY_TYPE, start, end, Constants.API_KEY, Constants.WEATHER_UNITS);

    }

}
