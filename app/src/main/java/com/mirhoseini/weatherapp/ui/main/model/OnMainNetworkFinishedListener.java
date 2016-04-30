package com.mirhoseini.weatherapp.ui.main.model;


import android.support.v4.util.Pair;

import com.mirhoseini.weatherapp.network.model.WeatherCurrent;
import com.mirhoseini.weatherapp.network.model.WeatherForecast;
import com.mirhoseini.weatherapp.network.model.WeatherHistory;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface OnMainNetworkFinishedListener {

    void onNetworkSuccess(Pair<WeatherCurrent, WeatherForecast> weatherPair);

    void onNetworkError(Throwable throwable);

    void onNetworkHistorySuccess(WeatherHistory weatherHistory);
}
