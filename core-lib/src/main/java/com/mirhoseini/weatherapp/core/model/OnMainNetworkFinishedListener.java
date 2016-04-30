package com.mirhoseini.weatherapp.core.model;


import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface OnMainNetworkFinishedListener {

    void onNetworkSuccess(WeatherMix weatherMix);

    void onNetworkError(Throwable throwable);

    void onNetworkHistorySuccess(WeatherHistory weatherHistory);
}
