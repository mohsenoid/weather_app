package com.mirhoseini.weatherapp.core.view;

import com.mirhoseini.weatherapp.core.service.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.service.model.WeatherMix;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface IViewMain {

    void showProgress();

    void hideProgress();

    void showToastMessage(String message);

    void showProgressMessage(String message);

    void showOfflineMessage();

    void showExitMessage();

    void showConnectionError();

    void showRetryMessage();

    void setWeatherValues(WeatherMix weatherMix);

    void setWeatherHistoryValues(WeatherHistory weatherHistory);
}
