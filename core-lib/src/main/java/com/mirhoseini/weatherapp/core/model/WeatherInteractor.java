package com.mirhoseini.weatherapp.core.model;


import org.openweathermap.model.WeatherHistory;
import org.openweathermap.model.WeatherMix;

import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface WeatherInteractor {

    Observable<WeatherMix> loadWeather(String city);

    Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end);

    void clearMemoryAndDiskCache();

    void clearMemoryCache();

}
