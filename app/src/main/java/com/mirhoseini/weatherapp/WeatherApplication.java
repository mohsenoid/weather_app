package com.mirhoseini.weatherapp;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;

import timber.log.Timber;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherApplication extends Application {

    private WeatherApplicationComponent component;

    public static WeatherApplication get(Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }

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
            Stetho.initializeWithDefaults(this);

            Timber.plant(new Timber.DebugTree());
            Timber.plant(new StethoTree());
        }

        component = DaggerWeatherApplicationComponent.builder()
                .weatherApplicationModule(getApplicationModule())
                .build();
    }
}
