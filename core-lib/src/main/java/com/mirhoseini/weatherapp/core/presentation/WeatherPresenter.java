package com.mirhoseini.weatherapp.core.presentation;

import com.mirhoseini.weatherapp.core.view.MainView;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface WeatherPresenter extends LifecyclePresenter {

    void setView(MainView view);

    void loadWeather(String city, boolean isConnected);

    void loadWeatherHistory(String city, boolean isConnected);

}
