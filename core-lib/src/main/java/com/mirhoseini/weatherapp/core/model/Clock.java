package com.mirhoseini.weatherapp.core.model;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface Clock {
    Clock REAL = System::currentTimeMillis;

    long millis();
}
