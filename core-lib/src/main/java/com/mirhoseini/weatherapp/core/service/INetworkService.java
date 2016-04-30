package com.mirhoseini.weatherapp.core.service;

import com.mirhoseini.weatherapp.core.service.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.service.model.WeatherMix;

import rx.Observable;

/**
 * Created by Mohsen on 4/30/16.
 */
public interface INetworkService {

    Observable<WeatherMix> loadWeather(String city);

    Observable<WeatherMix> loadWeather(long lat, long lon);

    Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end);

    Observable<WeatherHistory> loadWeatherHistory(long lat, long lon, long start, long end);
}
