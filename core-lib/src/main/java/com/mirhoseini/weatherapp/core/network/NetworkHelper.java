package com.mirhoseini.weatherapp.core.network;


import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.core.utils.IScheduler;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;


/**
 * Created by Mohsen on 30/04/16.
 */
public class NetworkHelper implements INetwork {

    private final Retrofit retrofit;
    private final Api api;

    private final IScheduler mScheduler;

    static NetworkHelper instance;

    public static NetworkHelper getInstance(IScheduler scheduler, boolean isDebug) {
        if (instance == null)
            instance = new NetworkHelper(scheduler, isDebug);

        return instance;
    }

    public NetworkHelper(IScheduler scheduler, boolean isDebug) {
        mScheduler = scheduler;

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
    public void loadWeather(String city, final OnNetworkFinishedListener<WeatherMix> listener) {

        Observable.combineLatest(api.getWeather(city, Constants.API_KEY)
                , api.getWeatherForecast(city, Constants.API_KEY)
                , (weatherCurrent, weatherForecast) -> new WeatherMix(weatherCurrent, weatherForecast))
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread())
                .subscribe(new Observer<WeatherMix>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do :)
                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onError(t);
                    }

                    @Override
                    public void onNext(WeatherMix weatherMix) {

                        listener.onSuccess(weatherMix);
                    }
                });

    }

    @Override
    public void loadWeather(long lat, long lon, final OnNetworkFinishedListener<WeatherMix> listener) {

        Observable.combineLatest(api.getWeather(lat, lon, Constants.API_KEY)
                , api.getWeatherForecast(lat, lon, Constants.API_KEY)
                , (weatherCurrent, weatherForecast) -> new WeatherMix(weatherCurrent, weatherForecast))
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread())
                .subscribe(new Observer<WeatherMix>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do :)
                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onError(t);
                    }

                    @Override
                    public void onNext(WeatherMix weatherMix) {
                        listener.onSuccess(weatherMix);
                    }
                });

    }

    @Override
    public void loadWeatherHistory(String city, long start, long end, final OnNetworkFinishedListener<WeatherHistory> listener) {

        api.getWeatherHistory(city, Constants.HISTORY_TYPE, start, end, Constants.API_KEY)
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread())
                .subscribe(new Observer<WeatherHistory>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do :)
                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onError(t);
                    }

                    @Override
                    public void onNext(WeatherHistory weatherHistory) {
                        listener.onSuccess(weatherHistory);
                    }
                });

    }

    @Override
    public void loadWeatherHistory(long lat, long lon, long start, long end, final OnNetworkFinishedListener<WeatherHistory> listener) {

        api.getWeatherHistory(lat, lon, Constants.HISTORY_TYPE, start, end, Constants.API_KEY)
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread())
                .subscribe(new Observer<WeatherHistory>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do :)
                    }

                    @Override
                    public void onError(Throwable t) {
                        listener.onError(t);
                    }

                    @Override
                    public void onNext(WeatherHistory weatherHistory) {
                        listener.onSuccess(weatherHistory);
                    }
                });
    }

}
