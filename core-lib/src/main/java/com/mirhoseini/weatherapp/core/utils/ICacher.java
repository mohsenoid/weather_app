package com.mirhoseini.weatherapp.core.utils;

import com.mirhoseini.weatherapp.core.service.model.WeatherMix;

import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface ICacher {
    Observable<WeatherMix> getWeather();

    Observable<Boolean> saveWeather(WeatherMix value);

    void clear();
}
