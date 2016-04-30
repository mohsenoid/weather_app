package com.mirhoseini.weatherapp.network;

import android.support.v4.util.Pair;

import com.mirhoseini.weatherapp.BuildConfig;
import com.mirhoseini.weatherapp.network.model.WeatherCurrent;
import com.mirhoseini.weatherapp.network.model.WeatherForecast;
import com.mirhoseini.weatherapp.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by Mohsen on 30/04/16.
 */
public class NetworkHelper {

    private final Retrofit retrofit;
    private final Api api;

    static NetworkHelper instance;

    public static NetworkHelper getInstance() {
        if (instance == null)
            instance = new NetworkHelper();

        return instance;
    }

    public NetworkHelper() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        //show retrofit logs if app is in Debug
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        builder.client(httpClient.build());

        retrofit = builder.build();

        api = retrofit.create(Api.class);
    }

    public void loadWeather(String city, long start, long end, final OnNetworkFinishedListener<Pair<WeatherCurrent, WeatherForecast>> listener) {
        Timber.d("Started Loading Weather using City: City=%s", city);

        Observable.combineLatest(api.getWeather(city, Constants.API_KEY)
                , api.getWeatherForecast(city, Constants.API_KEY)
                , (weatherCurrent, weatherForecast) -> new Pair<>(weatherCurrent, weatherForecast))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Pair<WeatherCurrent, WeatherForecast>>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do :)
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e("Network Error: %s", t.getMessage());
                        listener.onError(t);
                    }

                    @Override
                    public void onNext(Pair<WeatherCurrent, WeatherForecast> weatherPair) {
                        Timber.d("Received Network Response: %s", weatherPair.toString());

                        listener.onSuccess(weatherPair);
                    }
                });

    }

    public void loadWeather(long lat, long lon, final OnNetworkFinishedListener<Pair<WeatherCurrent, WeatherForecast>> listener) {
        Timber.d("Started Loading Weather using Location: Lat=%s, Lon=%s", lat, lon);

        Observable.combineLatest(api.getWeather(lat, lon, Constants.API_KEY)
                , api.getWeatherForecast(lat, lon, Constants.API_KEY)
                , (weatherCurrent, weatherForecast) -> new Pair<>(weatherCurrent, weatherForecast))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Pair<WeatherCurrent, WeatherForecast>>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do :)
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e("Network Error: %s", t.getMessage());
                        listener.onError(t);
                    }

                    @Override
                    public void onNext(Pair<WeatherCurrent, WeatherForecast> weatherPair) {
                        Timber.d("Received Network Response: %s", weatherPair.toString());

                        listener.onSuccess(weatherPair);
                    }
                });

    }

    public void loadWeatherHistory(String city, long start, long end, final OnNetworkFinishedListener<WeatherHistory> listener) {
        Timber.d("Started Loading Weather History using City: City=%s", city);

        api.getWeatherHistory(city, Constants.HISTORY_TYPE, start, end, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherHistory>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do :)
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e("Network Error: %s", t.getMessage());
                        listener.onError(t);
                    }

                    @Override
                    public void onNext(WeatherHistory weatherHistory) {
                        Timber.d("Received Network Response: %s", weatherHistory.toString());

                        listener.onSuccess(weatherHistory);
                    }
                });

    }

    public void loadWeatherHistory(long lat, long lon, long start, long end, final OnNetworkFinishedListener<WeatherHistory> listener) {
        Timber.d("Started Loading Weather History using Location: Lat=%s, Lon=%s", lat, lon);

        api.getWeatherHistory(lat, lon, Constants.HISTORY_TYPE, start, end, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherHistory>() {
                    @Override
                    public void onCompleted() {
                        //Nothing to do :)
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e("Network Error: %s", t.getMessage());
                        listener.onError(t);
                    }

                    @Override
                    public void onNext(WeatherHistory weatherHistory) {
                        Timber.d("Received Network Response: %s", weatherHistory.toString());

                        listener.onSuccess(weatherHistory);
                    }
                });
    }

}
