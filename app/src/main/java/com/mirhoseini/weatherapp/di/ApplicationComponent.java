package com.mirhoseini.weatherapp.di;

import com.mirhoseini.weatherapp.core.modules.ApiModule;
import com.mirhoseini.weatherapp.core.modules.CallAdapterModule;
import com.mirhoseini.weatherapp.core.modules.ClientModule;
import com.mirhoseini.weatherapp.core.modules.ConverterModule;
import com.mirhoseini.weatherapp.core.modules.GsonModule;
import com.mirhoseini.weatherapp.core.modules.LoggerModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 30/04/16.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class,
        ConverterModule.class,
        CallAdapterModule.class,
        ClientModule.class,
        LoggerModule.class,
        GsonModule.class
})
public interface ApplicationComponent {
    WeatherComponent plus(WeatherModule module);
}