package com.mirhoseini.weatherapp.core.model;

public interface Clock {
    long millis();

    Clock REAL = System::currentTimeMillis;
}
