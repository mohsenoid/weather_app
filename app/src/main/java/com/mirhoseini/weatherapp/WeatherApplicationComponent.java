package com.mirhoseini.weatherapp;

import com.mirhoseini.weatherapp.core.utils.ICacher;
import com.mirhoseini.weatherapp.core.utils.IScheduler;
import com.mirhoseini.weatherapp.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 30/04/16.
 */
@Component(modules = WeatherApplicationModule.class)
@Singleton
public interface WeatherApplicationComponent {

    IScheduler getScheduler();

    ICacher getCacher();

    MainActivity inject(MainActivity activity);
}