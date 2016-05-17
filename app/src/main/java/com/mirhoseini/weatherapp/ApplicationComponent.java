package com.mirhoseini.weatherapp;

import com.mirhoseini.weatherapp.module.ApiModule;
import com.mirhoseini.weatherapp.module.ApplicationModule;
import com.mirhoseini.weatherapp.module.ClientModule;
import com.mirhoseini.weatherapp.module.ConverterModule;
import com.mirhoseini.weatherapp.module.GsonModule;
import com.mirhoseini.weatherapp.module.LoggerModule;
import com.mirhoseini.weatherapp.module.CallAdapterModule;
import com.mirhoseini.weatherapp.ui.activity.MainActivity;

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
    void inject(MainActivity activity);
}