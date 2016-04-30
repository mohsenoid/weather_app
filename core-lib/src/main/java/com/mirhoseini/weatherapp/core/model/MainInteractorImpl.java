package com.mirhoseini.weatherapp.core.model;


import com.mirhoseini.weatherapp.core.network.INetwork;
import com.mirhoseini.weatherapp.core.network.OnNetworkFinishedListener;
import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;


/**
 * Created by Mohsen on 30/04/16.
 */
public class MainInteractorImpl implements MainInteractor {
    INetwork mNetwork;

    public MainInteractorImpl(INetwork network) {
        this.mNetwork = network;
    }

    @Override
    public void loadWeather(String city, final OnMainNetworkFinishedListener listener) {
        if (mNetwork != null) {//check if object is not destroyed

            // load City Weather data from network
            mNetwork.loadWeather(city, new OnNetworkFinishedListener<WeatherMix>() {
                @Override
                public void onSuccess(WeatherMix weatherMix) {
                    if (listener != null)
                        listener.onNetworkSuccess(weatherMix);
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
        if (mNetwork != null) {//check if object is not destroyed

            // load Location Weather data from network
            mNetwork.loadWeather(lat, lon, new OnNetworkFinishedListener<WeatherMix>() {
                @Override
                public void onSuccess(WeatherMix weatherMix) {
                    if (listener != null)
                        listener.onNetworkSuccess(weatherMix);
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
        if (mNetwork != null) {//check if object is not destroyed

            // load City Weather History data from network
            mNetwork.loadWeatherHistory(city, start, end, new OnNetworkFinishedListener<WeatherHistory>() {
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
        if (mNetwork != null) {//check if object is not destroyed

            // load Location Weather History data from network
            mNetwork.loadWeatherHistory(lat, lon, start, end, new OnNetworkFinishedListener<WeatherHistory>() {
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
        mNetwork = null;
    }

}
