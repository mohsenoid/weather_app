package com.mirhoseini.weatherapp;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.module.ApplicationModule;

import io.fabric.sdk.android.Fabric;
import okhttp3.HttpUrl;

/**
 * Created by Mohsen on 5/9/16.
 */
public abstract class WeatherApplication extends Application {
    private ApplicationComponent component;

    public static WeatherApplication get(Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }

    protected ApplicationModule getApplicationModule() {
        return new ApplicationModule(this, () -> HttpUrl.parse(Constants.BASE_URL), BuildConfig.DEBUG, 30);
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());

        component = DaggerApplicationComponent.builder()
                .applicationModule(getApplicationModule())
                .build();
    }

    abstract void initApplication();
}
