package com.mirhoseini.weatherapp.util;

import android.content.Context;

import com.google.gson.Gson;
import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.weatherapp.core.util.CacheProvider;
import com.mirhoseini.weatherapp.core.util.Constants;

import org.openweathermap.model.WeatherMix;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Mohsen on 30/04/16.
 */
public class AppCacheProvider implements CacheProvider {
    Context mContext;

    @Inject
    public AppCacheProvider(Context context) {
        mContext = context;
    }

    @Override
    public Observable<WeatherMix> getWeather() {
        return Observable.defer(() -> {
            Observable<WeatherMix> result;

            String data = AppSettings.getString(mContext, Constants.KEY_LAST_WEATHER, "");
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
            return Observable.just(AppSettings.setValue(mContext, Constants.KEY_LAST_WEATHER, data));
        });
    }

    @Override
    public void clear() {
        AppSettings.clearValue(mContext, Constants.KEY_LAST_WEATHER);
//        AppSettings.clearValue(mContext, Constants.LAST_WEATHER_HISTORY);
    }
}
