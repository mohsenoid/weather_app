package com.mirhoseini.weatherapp;

import android.content.Context;

import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.presentation.WeatherPresenterImpl;
import com.mirhoseini.weatherapp.core.service.WeatherServiceImpl;
import com.mirhoseini.weatherapp.core.utils.CacheProvider;
import com.mirhoseini.weatherapp.core.utils.SchedulerProvider;
import com.mirhoseini.weatherapp.utils.AppCacheProvider;
import com.mirhoseini.weatherapp.utils.AppSchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 30/04/16.
 */
@Module
public class WeatherApplicationModule {
    private WeatherApplication weatherApplication;

    public WeatherApplicationModule(WeatherApplication weatherApplication) {
        this.weatherApplication = weatherApplication;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return weatherApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public CacheProvider provideAppCache() {
        return new AppCacheProvider(weatherApplication);
    }

    @Provides
    @Singleton
    public SchedulerProvider provideAppScheduler() {
        return new AppSchedulerProvider();
    }

    @Provides
    public Clock provideClock() {
        return Clock.REAL;
    }

    @Provides
    public WeatherPresenterImpl provideWeatherPresenter(CacheProvider cacheProvider, WeatherServiceImpl weatherService, SchedulerProvider schedulerProvider) {
        return new WeatherPresenterImpl(cacheProvider, weatherService, schedulerProvider);
    }

    @Provides
    @Singleton
    public WeatherServiceImpl provideWeatherNetworkService() {
        return new WeatherServiceImpl(BuildConfig.DEBUG);
    }
}
