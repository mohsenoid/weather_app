package com.mirhoseini.weatherapp.core.di;

import com.mirhoseini.weatherapp.core.service.Api;
import com.mirhoseini.weatherapp.core.service.WeatherApiService;
import com.mirhoseini.weatherapp.core.service.WeatherApiServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.BaseUrl;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Mohsen on 5/17/16.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public WeatherApiService provideWeatherApiService(Retrofit retrofit) {
        return new WeatherApiServiceImpl(retrofit.create(Api.class));
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(BaseUrl baseUrl, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
    }


}
