package com.mirhoseini.weatherapp;

import com.mirhoseini.weatherapp.core.utils.ICacher;
import com.mirhoseini.weatherapp.core.utils.IScheduler;
import com.mirhoseini.weatherapp.ui.activity.MainActivity;
import com.mirhoseini.weatherapp.utils.AppCacher;
import com.mirhoseini.weatherapp.utils.AppScheduler;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 5/1/16.
 */

@Component(modules = WeatherApplicationModule.class)
@Singleton
public interface WeatherApplicationComponent {

    IScheduler getScheduler();

    ICacher getCacher();

    MainActivity inject(MainActivity activity);
}