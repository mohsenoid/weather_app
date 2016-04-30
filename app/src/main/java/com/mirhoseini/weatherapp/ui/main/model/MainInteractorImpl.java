package com.mirhoseini.weatherapp.ui.main.model;


import android.support.v4.util.Pair;

import com.mirhoseini.weatherapp.network.NetworkHelper;
import com.mirhoseini.weatherapp.network.OnNetworkFinishedListener;
import com.mirhoseini.weatherapp.network.model.WeatherCurrent;
import com.mirhoseini.weatherapp.network.model.WeatherForecast;
import com.mirhoseini.weatherapp.network.model.WeatherHistory;


/**
 * Created by Mohsen on 30/04/16.
 */
public class MainInteractorImpl implements MainInteractor {
    NetworkHelper mNetworkHelper;

    public MainInteractorImpl() {
        mNetworkHelper = NetworkHelper.getInstance();
    }

    @Override
    public void loadWeather(String city, final OnMainNetworkFinishedListener listener) {
        if (mNetworkHelper != null) {//check if object is not destroyed

            // load City Weather data from network
            mNetworkHelper.loadWeather(city, new OnNetworkFinishedListener<Pair<WeatherCurrent, WeatherForecast>>() {
                @Override
                public void onSuccess(Pair<WeatherCurrent, WeatherForecast> weatherPair) {
                    if (listener != null)
                        listener.onNetworkSuccess(weatherPair);
                }

                @Override
                public void onError(Throwable throwable) {
                    if (listener != null)
                        listener.onNetworkError(throwable);
                }
            });

        }
    }

    @Override
    public void loadWeather(long lat, long lon, final OnMainNetworkFinishedListener listener) {
        if (mNetworkHelper != null) {//check if object is not destroyed

            // load Location Weather data from network
            mNetworkHelper.loadWeather(lat, lon, new OnNetworkFinishedListener<Pair<WeatherCurrent, WeatherForecast>>() {
                @Override
                public void onSuccess(Pair<WeatherCurrent, WeatherForecast> weatherPair) {
                    if (listener != null)
                        listener.onNetworkSuccess(weatherPair);
                }

                @Override
                public void onError(Throwable throwable) {
                    if (listener != null)
                        listener.onNetworkError(throwable);
                }
            });

        }
    }

    @Override
    public void loadWeatherHistory(String city, long start, long end, OnMainNetworkFinishedListener listener) {
        if (mNetworkHelper != null) {//check if object is not destroyed

            // load City Weather History data from network
            mNetworkHelper.loadWeatherHistory(city, start, end, new OnNetworkFinishedListener<WeatherHistory>() {
                @Override
                public void onSuccess(WeatherHistory weatherHistory) {
                    if (listener != null)
                        listener.onNetworkHistorySuccess(weatherHistory);
                }

                @Override
                public void onError(Throwable throwable) {
                    if (listener != null)
                        listener.onNetworkError(throwable);
                }
            });

        }
    }

    @Override
    public void loadWeatherHistory(long lat, long lon, long start, long end, OnMainNetworkFinishedListener listener) {
        if (mNetworkHelper != null) {//check if object is not destroyed

            // load Location Weather History data from network
            mNetworkHelper.loadWeatherHistory(lat, lon, start, end, new OnNetworkFinishedListener<WeatherHistory>() {
                @Override
                public void onSuccess(WeatherHistory weatherHistory) {
                    if (listener != null)
                        listener.onNetworkHistorySuccess(weatherHistory);
                }

                @Override
                public void onError(Throwable throwable) {
                    if (listener != null)
                        listener.onNetworkError(throwable);
                }
            });

        }
    }


    @Override
    public void onDestroy() {
        mNetworkHelper = null;
    }

}
