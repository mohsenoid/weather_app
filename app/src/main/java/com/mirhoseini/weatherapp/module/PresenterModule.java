package com.mirhoseini.weatherapp.module;

import com.mirhoseini.weatherapp.core.presentation.WeatherPresenter;
import com.mirhoseini.weatherapp.core.presentation.WeatherPresenterImpl;
import com.mirhoseini.weatherapp.core.service.WeatherApiService;
import com.mirhoseini.weatherapp.core.utils.CacheProvider;
import com.mirhoseini.weatherapp.core.utils.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 5/17/16.
 */
@Module
public class PresenterModule {
    @Provides
    public WeatherPresenter provideWeatherPresenter(CacheProvider cacheProvider, WeatherApiService weatherApiService, SchedulerProvider schedulerProvider) {
        return new WeatherPresenterImpl(cacheProvider, weatherApiService, schedulerProvider);
    }
}
