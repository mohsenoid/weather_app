package com.mirhoseini.weatherapp;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import timber.log.Timber;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherApplication extends Application {

    private static final String TAG = WeatherApplication.class.getSimpleName();

    public static WeatherApplication get(Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }

}
