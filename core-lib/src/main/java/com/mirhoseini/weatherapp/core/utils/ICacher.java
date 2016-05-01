package com.mirhoseini.weatherapp.core.utils;

import com.mirhoseini.weatherapp.core.service.model.WeatherMix;

import rx.Observable;

/**
 * Created by Mohsen on 4/30/16.
 */
public interface ICacher {
    Observable<WeatherMix> getWeather();

    Observable<Boolean> saveWeather(WeatherMix value);

//    Observable<WeatherHistory> getWeatherHistory();

//    Observable<Boolean> saveWeatherHistory(WeatherHistory value);

    void clear();
}
