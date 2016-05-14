package com.mirhoseini.weatherapp.core.utils;

import org.openweathermap.model.WeatherMix;

import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface CacheProvider {
    Observable<WeatherMix> getWeather();

    Observable<Boolean> saveWeather(WeatherMix value);

    void clear();
}
