package com.mirhoseini.weatherapp;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherApplication extends Application {

    public static WeatherApplication get(Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }

    private WeatherApplicationComponent component;

    protected WeatherApplicationModule getApplicationModule() {
        return new WeatherApplicationModule(this);
    }

    public WeatherApplicationComponent getComponent() {
        return component;
    }

    public void setComponent(WeatherApplicationComponent component) {
        this.component = component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        component = DaggerWeatherApplicationComponent.builder()
                .weatherApplicationModule(getApplicationModule())
                .build();
    }
}
