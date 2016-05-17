package com.mirhoseini.weatherapp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.mirhoseini.weatherapp.di.ApplicationComponent;
import com.mirhoseini.weatherapp.di.ApplicationModule;
import com.mirhoseini.weatherapp.di.DaggerApplicationComponent;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Mohsen on 5/9/16.
 */
public abstract class WeatherApplication extends Application {
    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    protected ApplicationModule getApplicationModule() {
        return new ApplicationModule(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

        component = DaggerApplicationComponent.builder()
                .applicationModule(getApplicationModule())
                .build();
    }

    abstract void initApplication();
}
