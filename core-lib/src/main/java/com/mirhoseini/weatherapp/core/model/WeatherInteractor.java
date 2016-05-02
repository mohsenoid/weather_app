package com.mirhoseini.weatherapp.core.model;


import com.mirhoseini.weatherapp.core.service.INetworkService;
import com.mirhoseini.weatherapp.core.service.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.service.model.WeatherMix;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.core.utils.ICacher;
import com.mirhoseini.weatherapp.core.utils.IScheduler;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;


/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherInteractor implements IInteractor {
    private ICacher mDiskCache;
    private INetworkService mNetworkService;
    private IScheduler mScheduler;
    private Clock mClock;

    private WeatherMix memoryCacheWeather;

    private ReplaySubject<WeatherMix> weatherSubject;
    private ReplaySubject<WeatherHistory> weatherHistorySubject;
    private Subscription weatherSubscription, weatherHistorySubscription;

    public WeatherInteractor(ICacher diskCache, INetworkService networkService, IScheduler scheduler, Clock clock) {
        mDiskCache = diskCache;
        mNetworkService = networkService;
        mScheduler = scheduler;
        mClock = clock;
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
        return mNetworkService.loadWeather(city)
                .doOnNext(entity -> {
                    entity.setDt(mClock.millis());
                    memoryCacheWeather = entity;
                })
                .flatMap(entity -> mDiskCache.saveWeather(entity).map(__ -> entity))
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread());
    }

    private Observable<WeatherHistory> networkWeatherHistory(String city, long start, long end) {
        return mNetworkService.loadWeatherHistory(city, start, end)
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread());
    }

    private Observable<WeatherMix> diskWeather() {
        return mDiskCache.getWeather()
                .doOnNext(entity -> memoryCacheWeather = entity)
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread());
    }

    private Observable<WeatherMix> memoryWeather() {
        return Observable.just(memoryCacheWeather);
    }

    public void clearMemoryAndDiskCache() {
        mDiskCache.clear();
        clearMemoryCache();
    }

    public void clearMemoryCache() {
        memoryCacheWeather = null;
    }

    private boolean isUpToDate(WeatherMix entity) {
        return mClock.millis() - entity.getDt() < Constants.STALE_MS;
    }

    private boolean isSameCity(String city, WeatherMix entity) {
        return entity.getWeatherCurrent().getName().equalsIgnoreCase(city);
    }

    @Override
    public void onDestroy() {
        mNetworkService = null;
        mDiskCache = null;
        mScheduler = null;
        mClock = null;
    }

}
