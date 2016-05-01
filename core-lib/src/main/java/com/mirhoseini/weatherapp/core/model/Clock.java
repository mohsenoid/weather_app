package com.mirhoseini.weatherapp.core.model;

public interface Clock {
    Clock REAL = System::currentTimeMillis;

    long millis();
}
