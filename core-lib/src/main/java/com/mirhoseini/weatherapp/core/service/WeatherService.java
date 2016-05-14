package com.mirhoseini.weatherapp.core.service;

import org.openweathermap.model.WeatherHistory;
import org.openweathermap.model.WeatherMix;

import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface WeatherService {

    Observable<WeatherMix> loadWeather(String city);

    Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end);

}
