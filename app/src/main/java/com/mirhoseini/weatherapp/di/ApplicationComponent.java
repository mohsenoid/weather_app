package com.mirhoseini.weatherapp.di;

import com.mirhoseini.weatherapp.core.di.ApiModule;
import com.mirhoseini.weatherapp.core.di.CacheModule;
import com.mirhoseini.weatherapp.core.di.CallAdapterModule;
import com.mirhoseini.weatherapp.core.di.ClientModule;
import com.mirhoseini.weatherapp.core.di.ConverterModule;
import com.mirhoseini.weatherapp.core.di.GsonModule;
import com.mirhoseini.weatherapp.core.di.InterceptorModule;
import com.mirhoseini.weatherapp.core.di.LoggerModule;
import com.mirhoseini.weatherapp.core.di.WeatherModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 30/04/16.
 */
@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ConverterModule.class,
        CallAdapterModule.class,
        CacheModule.class,
        InterceptorModule.class,
        ClientModule.class,
        LoggerModule.class,
        GsonModule.class
})
public interface ApplicationComponent {
    WeatherComponent plus(WeatherModule module);
}