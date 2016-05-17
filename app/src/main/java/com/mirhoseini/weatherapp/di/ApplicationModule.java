package com.mirhoseini.weatherapp.di;

import android.content.Context;

import com.mirhoseini.weatherapp.BuildConfig;
import com.mirhoseini.weatherapp.WeatherApplication;
import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.utils.CacheProvider;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.core.utils.SchedulerProvider;
import com.mirhoseini.weatherapp.util.AppCacheProvider;
import com.mirhoseini.weatherapp.util.AppSchedulerProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import retrofit2.BaseUrl;

/**
 * Created by Mohsen on 30/04/16.
 */
@Module
public class ApplicationModule {
    private WeatherApplication weatherApplication;

    public ApplicationModule(WeatherApplication weatherApplication) {
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
        return BuildConfig.DEBUG;
    }

    @Provides
    @Singleton
    @Named("networkTimeoutInSeconds")
    public int provideNetworkTimeoutInSeconds() {
        return 30;
    }

    @Provides
    @Singleton
    public BaseUrl provideEndpoint() {
        return () -> HttpUrl.parse(Constants.BASE_URL);
    }

    @Provides
    public Clock provideClock() {
        return Clock.REAL;
    }

    @Provides
    @Singleton
    public CacheProvider provideAppCache(Context context) {
        return new AppCacheProvider(context);
    }

    @Provides
    @Singleton
    public SchedulerProvider provideAppScheduler() {
        return new AppSchedulerProvider();
    }

}
