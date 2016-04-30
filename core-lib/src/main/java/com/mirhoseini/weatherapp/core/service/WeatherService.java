package com.mirhoseini.weatherapp.core.service;


import com.mirhoseini.weatherapp.core.network.Api;
import com.mirhoseini.weatherapp.core.network.INetworkService;
import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;
import com.mirhoseini.weatherapp.core.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherService implements INetworkService {

    private final Retrofit retrofit;
    private final Api api;

    static WeatherService instance;

    public static WeatherService getInstance(boolean isDebug) {
        if (instance == null)
            instance = new WeatherService(isDebug);

        return instance;
    }

    public WeatherService(boolean isDebug) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        //show retrofit logs if app is in Debug
        if (isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        builder.client(httpClient.build());

        retrofit = builder.build();

        api = retrofit.create(Api.class);
    }

    @Override
    public Observable<WeatherMix> loadWeather(String city) {

        return Observable.combineLatest(api.getWeather(city, Constants.API_KEY)
                , api.getWeatherForecast(city, Constants.API_KEY)
                , (weatherCurrent, weatherForecast) -> new WeatherMix(weatherCurrent, weatherForecast));

    }

    @Override
    public Observable<WeatherMix> loadWeather(long lat, long lon) {

        return Observable.combineLatest(api.getWeather(lat, lon, Constants.API_KEY)
                , api.getWeatherForecast(lat, lon, Constants.API_KEY)
                , (weatherCurrent, weatherForecast) -> new WeatherMix(weatherCurrent, weatherForecast));

    }

    @Override
    public Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end) {

        return api.getWeatherHistory(city, Constants.HISTORY_TYPE, start, end, Constants.API_KEY);

    }

    @Override
    public Observable<WeatherHistory> loadWeatherHistory(long lat, long lon, long start, long end) {

        return api.getWeatherHistory(lat, lon, Constants.HISTORY_TYPE, start, end, Constants.API_KEY);
    }

}
