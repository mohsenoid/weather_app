package com.mirhoseini.weatherapp;

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
