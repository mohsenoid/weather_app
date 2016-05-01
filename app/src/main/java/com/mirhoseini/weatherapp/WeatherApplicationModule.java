package com.mirhoseini.weatherapp;

import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.utils.ICacher;
import com.mirhoseini.weatherapp.core.utils.IScheduler;
import com.mirhoseini.weatherapp.utils.AppCacher;
import com.mirhoseini.weatherapp.utils.AppScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 5/1/16.
 */
@Module
public class WeatherApplicationModule {
    private WeatherApplication app;

    public WeatherApplicationModule(WeatherApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public ICacher provideAppCacher() {
        return new AppCacher(app);
    }

    @Provides
    @Singleton
    public IScheduler provideAppScheduler() {
        return new AppScheduler();
    }

    @Provides
    public Clock provideClock() {
        return Clock.REAL;
    }

}
