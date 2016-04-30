package com.mirhoseini.weatherapp.ui.main.model;

import com.mirhoseini.weatherapp.ui.main.presenter.MainPresenterImpl;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface MainInteractor {

    void onDestroy();

    void loadWeather(String city, OnMainNetworkFinishedListener listener);

    void loadWeather(long lat, long lon, OnMainNetworkFinishedListener listener);

    void loadWeatherHistory(String city, long start, long end, OnMainNetworkFinishedListener listener);

    void loadWeatherHistory(long lat, long lon, long start, long end, OnMainNetworkFinishedListener listener);
}
