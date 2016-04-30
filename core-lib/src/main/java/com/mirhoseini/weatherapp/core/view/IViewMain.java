package com.mirhoseini.weatherapp.core.view;

import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface IViewMain {

    void showProgress();

    void hideProgress();

    void showToastMessage(String message);

    void showProgressMessage(String message);

    void showOfflineMessage();

    void exit();

    void showExitMessage();

    void showConnectionError();

    void showRetryMessage();

    void setWeatherValues(WeatherMix weatherMix);

    void setWeatherHistoryValues(WeatherHistory weatherHistory);
}
