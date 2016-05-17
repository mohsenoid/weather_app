package com.mirhoseini.weatherapp.module;

import com.mirhoseini.weatherapp.core.utils.SchedulerProvider;
import com.mirhoseini.weatherapp.utils.AppSchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 5/17/16.
 */
@Module
public class SchedulerModule {
    @Provides
    @Singleton
    public SchedulerProvider provideAppScheduler() {
        return new AppSchedulerProvider();
    }
}
