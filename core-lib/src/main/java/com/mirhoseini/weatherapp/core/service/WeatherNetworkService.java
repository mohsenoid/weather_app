package com.mirhoseini.weatherapp.core.service;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mirhoseini.weatherapp.core.service.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.service.model.WeatherMix;
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
public class WeatherNetworkService implements INetworkService {

    private final Retrofit retrofit;
    private final Api api;

    public WeatherNetworkService(boolean isDebug) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        //show retrofit logs if app is in Debug mode
        if (isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            httpClient.networkInterceptors().add(new StethoInterceptor());
        }

        builder.client(httpClient.build());

        retrofit = builder.build();

        api = retrofit.create(Api.class);
    }

    @Override
    public Observable<WeatherMix> loadWeather(String city) {

        return Observable.combineLatest(api.getWeather(city, Constants.API_KEY, Constants.WEATHER_UNITS)
                , api.getWeatherForecast(city, Constants.API_KEY, Constants.WEATHER_UNITS, Constants.FORECAST_DAY_COUNT)
                , (weatherCurrent, weatherForecast) -> new WeatherMix(weatherCurrent, weatherForecast));

    }

    // Weather can be loaded using GPS data, but removed because of time.
    /*
    @Override
    public Observable<WeatherMix> loadWeather(long lat, long lon) {

        return Observable.combineLatest(api.getWeather(lat, lon, Constants.API_KEY)
                , api.getWeatherForecast(lat, lon, Constants.API_KEY)
                , (weatherCurrent, weatherForecast) -> new WeatherMix(weatherCurrent, weatherForecast));

    }
    */

    @Override
    public Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end) {

        return api.getWeatherHistory(city, Constants.HISTORY_TYPE, start, end, Constants.API_KEY, Constants.WEATHER_UNITS);

    }

    // Weather can be loaded using GPS data, but removed because of time.
    /*
    @Override
    public Observable<WeatherHistory> loadWeatherHistory(long lat, long lon, long start, long end) {

        return api.getWeatherHistory(lat, lon, Constants.HISTORY_TYPE, start, end, Constants.API_KEY);
    }
    */

}
