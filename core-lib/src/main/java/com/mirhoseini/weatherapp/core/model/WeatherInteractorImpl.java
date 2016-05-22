package com.mirhoseini.weatherapp.core.model;


import com.mirhoseini.weatherapp.core.di.WeatherScope;
import com.mirhoseini.weatherapp.core.service.WeatherApiService;
import com.mirhoseini.weatherapp.core.util.CacheProvider;
import com.mirhoseini.weatherapp.core.util.Constants;
import com.mirhoseini.weatherapp.core.util.SchedulerProvider;

import org.openweathermap.model.WeatherHistory;
import org.openweathermap.model.WeatherMix;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;


/**
 * Created by Mohsen on 30/04/16.
 */
@WeatherScope
public class WeatherInteractorImpl implements WeatherInteractor {
    private CacheProvider diskCache;
    private WeatherApiService weatherApiService;
    private SchedulerProvider scheduler;
    private Clock clock;

    private WeatherMix memoryCacheWeather;

    private ReplaySubject<WeatherMix> weatherSubject;
    private ReplaySubject<WeatherHistory> weatherHistorySubject;
    private Subscription weatherSubscription, weatherHistorySubscription;

    @Inject
    public WeatherInteractorImpl(CacheProvider diskCache, WeatherApiService weatherApiService, SchedulerProvider scheduler, Clock clock) {
        this.diskCache = diskCache;
        this.weatherApiService = weatherApiService;
        this.scheduler = scheduler;
        this.clock = clock;
    }


    @Override
    public Observable<WeatherMix> loadWeather(String city) {
        if (weatherSubscription == null || weatherSubscription.isUnsubscribed()) {
            weatherSubject = ReplaySubject.create();

            weatherSubscription = Observable.concat(memoryWeather(), diskWeather(), networkWeather(city))
                    .first(entity -> entity != null && isSameCity(city, entity) && isUpToDate(entity))
                    .subscribe(weatherSubject);
        }

        return weatherSubject.asObservable();

    }

    @Override
    public Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end) {
        if (weatherHistorySubscription == null || weatherHistorySubscription.isUnsubscribed()) {
            weatherHistorySubject = ReplaySubject.create();

            weatherHistorySubscription = networkWeatherHistory(city, start, end)
                    .subscribe(weatherHistorySubject);
        }

        return weatherHistorySubject.asObservable();

    }

    private Observable<WeatherMix> networkWeather(String city) {
        return weatherApiService.loadWeather(city)
                .doOnNext(entity -> {
                    entity.setDt(clock.millis());
                    memoryCacheWeather = entity;
                })
                .flatMap(entity -> diskCache.saveWeather(entity).map(__ -> entity))
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

    private Observable<WeatherHistory> networkWeatherHistory(String city, long start, long end) {
        return weatherApiService.loadWeatherHistory(city, start, end)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

    private Observable<WeatherMix> diskWeather() {
        return diskCache.getWeather()
                .doOnNext(entity -> memoryCacheWeather = entity)
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

    private Observable<WeatherMix> memoryWeather() {
        return Observable.just(memoryCacheWeather);
    }

    public void clearMemoryAndDiskCache() {
        diskCache.clear();
        clearMemoryCache();
    }

    public void clearMemoryCache() {
        memoryCacheWeather = null;
    }

    private boolean isUpToDate(WeatherMix entity) {
        return clock.millis() - entity.getDt() < Constants.STALE_MS;
    }

    private boolean isSameCity(String city, WeatherMix entity) {
        return entity.getWeatherCurrent().getName().equalsIgnoreCase(city);
    }

    @Override
    public void onDestroy() {
        if (weatherSubscription != null && !weatherSubscription.isUnsubscribed()) {
            weatherSubscription.unsubscribe();
        }

        if (weatherHistorySubscription != null && !weatherHistorySubscription.isUnsubscribed()) {
            weatherHistorySubscription.unsubscribe();
        }

        weatherApiService = null;
        diskCache = null;
        scheduler = null;
        clock = null;
    }

}
