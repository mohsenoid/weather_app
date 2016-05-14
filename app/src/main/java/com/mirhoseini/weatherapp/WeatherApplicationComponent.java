package com.mirhoseini.weatherapp;

import com.mirhoseini.weatherapp.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 30/04/16.
 */
@Singleton
@Component(modules = WeatherApplicationModule.class)
public interface WeatherApplicationComponent {
    void inject(MainActivity activity);
}