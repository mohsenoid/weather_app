package com.mirhoseini.weatherapp.ui.main.presenter;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface MainPresenter {

    void onResume();

    void onDestroy();

    boolean onBackPressed();

    void loadWeather(String city, boolean isConnected);

    void loadWeather(long lat, long lon, boolean isConnected);

    void loadWeatherHistory(String city, boolean isConnected);

    void loadWeatherHostory(long lat, long lon, boolean isConnected);
}
