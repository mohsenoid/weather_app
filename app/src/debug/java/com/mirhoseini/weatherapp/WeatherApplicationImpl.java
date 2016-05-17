package com.mirhoseini.weatherapp;

import timber.log.Timber;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherApplicationImpl extends WeatherApplication {

    @Override
    void initApplication() {
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                //adding line number to tag
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }
}
