package com.mirhoseini.weatherapp;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.mirhoseini.weatherapp.di.AndroidModule;
import com.mirhoseini.weatherapp.di.ApplicationComponent;
import com.mirhoseini.weatherapp.di.DaggerApplicationComponent;

/**
 * Created by Mohsen on 5/9/16.
 */
public abstract class WeatherApplication extends Application {
    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    protected AndroidModule getAndroidModule() {
        return new AndroidModule(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

        initApplication();

        component = DaggerApplicationComponent.builder()
                .androidModule(getAndroidModule())
                .build();
    }

    abstract void initApplication();
}
