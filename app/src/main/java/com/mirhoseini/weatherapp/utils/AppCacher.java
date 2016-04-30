package com.mirhoseini.weatherapp.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.core.utils.ICacher;

import rx.Observable;

/**
 * Created by Mohsen on 4/30/16.
 */
public class AppCacher implements ICacher {
    Context mContext;

    public AppCacher(Context context) {
        mContext = context;
    }

    @Override
    public Observable<WeatherMix> getWeather() {
        return Observable.defer(() -> {
            Observable<WeatherMix> result;

            String data = AppSettings.getString(mContext, Constants.LAST_WEATHER, "");
            if (data.isEmpty()) {
                result = Observable.just(null);
            } else {
                Gson gson = new Gson();
                WeatherMix weatherMix = gson.fromJson(data, WeatherMix.class);
                result = Observable.just(weatherMix);
            }
            return result;
        });
    }

    @Override
    public Observable<Boolean> saveWeather(WeatherMix value) {
        return Observable.defer(() -> {
            Gson gson = new Gson();
            String data = gson.toJson(value);
            return Observable.just(AppSettings.setValue(mContext, Constants.LAST_WEATHER, data));
        });
    }

    @Override
    public Observable<WeatherHistory> getWeatherHistory() {
        return Observable.defer(() -> {
            Observable<WeatherHistory> result;

            String data = AppSettings.getString(mContext, Constants.LAST_WEATHER_HISTORY, "");
            if (data.isEmpty()) {
                result = Observable.just(null);
            } else {
                Gson gson = new Gson();
                WeatherHistory weatherHistory = gson.fromJson(data, WeatherHistory.class);
                result = Observable.just(weatherHistory);
            }
            return result;
        });
    }

    @Override
    public Observable<Boolean> saveWeatherHistory(WeatherHistory value) {
        return Observable.defer(() -> {
            Gson gson = new Gson();
            String data = gson.toJson(value);
            return Observable.just(AppSettings.setValue(mContext, Constants.LAST_WEATHER_HISTORY, data));
        });
    }

    @Override
    public void clear() {
        AppSettings.clearValue(mContext, Constants.LAST_WEATHER);
        AppSettings.clearValue(mContext, Constants.LAST_WEATHER_HISTORY);
    }
}
