package com.mirhoseini.weatherapp.ui.main.view;

import android.support.v4.util.Pair;

import com.mirhoseini.weatherapp.network.model.WeatherCurrent;
import com.mirhoseini.weatherapp.network.model.WeatherForecast;
import com.mirhoseini.weatherapp.network.model.WeatherHistory;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface MainView {

    void showProgress();

    void hideProgress();

    void showToastMessage(String message);

    void showProgressMessage(String message);

    void showOfflineMessage();

    void exit();

    void showExitMessage();

    void showConnectionError();

    void showRetryMessage();

    void setWeatherValues(Pair<WeatherCurrent, WeatherForecast> weatherPair);

    void setWeatherHistoryValues(WeatherHistory weatherHistory);
}
