package com.mirhoseini.weatherapp;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherApplicationImpl extends WeatherApplication {

    @Override
    void initApplication() {
        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());
    }
}
