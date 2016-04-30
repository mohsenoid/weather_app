package com.mirhoseini.weatherapp.core.service;

import com.mirhoseini.weatherapp.core.network.INetwork;

/**
 * Created by Mohsen on 4/30/16.
 */
public class WeatherService {

    public void getWeather(INetwork network) {
        network.loadWeather("",null);
    }
}
