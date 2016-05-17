package com.mirhoseini.weatherapp.module;

import android.content.Context;

import com.mirhoseini.weatherapp.core.utils.CacheProvider;
import com.mirhoseini.weatherapp.utils.AppCacheProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 5/17/16.
 */
@Module
public class CacheModule {
    @Provides
    @Singleton
    public CacheProvider provideAppCache(Context context) {
        return new AppCacheProvider(context);
    }
}
