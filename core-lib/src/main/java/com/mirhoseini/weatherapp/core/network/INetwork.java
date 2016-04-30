package com.mirhoseini.weatherapp.core.network;

import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;

/**
 * Created by Mohsen on 4/30/16.
 */
public interface INetwork {

    void loadWeather(String city, final OnNetworkFinishedListener<WeatherMix> listener);

    void loadWeather(long lat, long lon, OnNetworkFinishedListener<WeatherMix> listener);

    void loadWeatherHistory(String city, long start, long end, OnNetworkFinishedListener<WeatherHistory> listener);

    void loadWeatherHistory(long lat, long lon, long start, long end, OnNetworkFinishedListener<WeatherHistory> listener);
}
