package com.mirhoseini.weatherapp.module;

import android.content.Context;

import com.mirhoseini.weatherapp.WeatherApplication;
import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.presentation.WeatherPresenter;
import com.mirhoseini.weatherapp.core.presentation.WeatherPresenterImpl;
import com.mirhoseini.weatherapp.core.service.WeatherApiService;
import com.mirhoseini.weatherapp.core.utils.CacheProvider;
import com.mirhoseini.weatherapp.core.utils.SchedulerProvider;
import com.mirhoseini.weatherapp.utils.AppCacheProvider;
import com.mirhoseini.weatherapp.utils.AppSchedulerProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.BaseUrl;

/**
 * Created by Mohsen on 30/04/16.
 */
@Module
public class ApplicationModule {
    private WeatherApplication weatherApplication;
    private boolean isDebug = false;
    private int networkTimeoutInSeconds;
    private BaseUrl endpoint;

    public ApplicationModule(WeatherApplication weatherApplication, BaseUrl endpoint, boolean isDebug, int networkTimeoutInSeconds) {
        this.endpoint = endpoint;
        this.isDebug = isDebug;
        this.networkTimeoutInSeconds = networkTimeoutInSeconds;
        this.weatherApplication = weatherApplication;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return weatherApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    @Named("isDebug")
    public boolean provideIsDebug() {
        return isDebug;
    }

    @Provides
    @Singleton
    @Named("networkTimeoutInSeconds")
    public int provideNetworkTimeoutInSeconds() {
        return networkTimeoutInSeconds;
    }

    @Provides
    @Singleton
    public BaseUrl provideEndpoint(){
        return endpoint;
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
    public WeatherPresenter provideWeatherPresenter(CacheProvider cacheProvider, WeatherApiService weatherApiService, SchedulerProvider schedulerProvider) {
        return new WeatherPresenterImpl(cacheProvider, weatherApiService, schedulerProvider);
    }


}
