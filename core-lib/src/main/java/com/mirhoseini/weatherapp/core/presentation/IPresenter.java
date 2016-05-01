package com.mirhoseini.weatherapp.core.presentation;

import com.mirhoseini.weatherapp.core.view.IViewMain;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface IPresenter {

    void setView(IViewMain view);

    void onDestroy();

    void loadWeather(String city, boolean isConnected);

//    void loadWeather(long lat, long lon, boolean isConnected);

    void loadWeatherHistory(String city, boolean isConnected);

//    void loadWeatherHostory(long lat, long lon, boolean isConnected);
}
