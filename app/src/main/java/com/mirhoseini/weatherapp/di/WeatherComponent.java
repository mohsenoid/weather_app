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
import com.mirhoseini.weatherapp.core.di.WeatherScope;
import com.mirhoseini.weatherapp.ui.activity.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Mohsen on 5/17/16.
 */
@WeatherScope
@Subcomponent(modules = {
        WeatherModule.class
})
public interface WeatherComponent {

    void inject(MainActivity activity);

}
