package com.mirhoseini.weatherapp.core.model;


import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;

import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface IInteractor {

    Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end);

    Observable<WeatherHistory> loadWeatherHistory(long lat, long lon, long start, long end);

    void onDestroy();

    Observable<WeatherMix> loadWeather(String city);

    Observable<WeatherMix> loadWeather(long lat, long lon);

}
