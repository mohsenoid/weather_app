package com.mirhoseini.weatherapp.core.model;


import com.mirhoseini.weatherapp.core.service.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.service.model.WeatherMix;

import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface IInteractor {

    Observable<WeatherMix> loadWeather(String city);

    Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end);

    void onDestroy();

}
