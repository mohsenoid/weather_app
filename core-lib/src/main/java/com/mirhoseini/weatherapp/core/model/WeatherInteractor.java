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
    private WeatherHistory memoryCacheWeatherHistory;

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
    public Observable<WeatherMix> loadWeather(long lat, long lon) {
        if (weatherSubscription == null || weatherSubscription.isUnsubscribed()) {
            weatherSubject = ReplaySubject.create();

            weatherSubscription = Observable.concat(memoryWeather(), diskWeather(), networkWeather(lat, lon))
                    .first(entity -> entity != null && isSameCity(lat, lon, entity) && isUpToDate(entity))
                    .subscribe(weatherSubject);
        }

        return weatherSubject.asObservable();

    }


    @Override
    public Observable<WeatherHistory> loadWeatherHistory(String city, long start, long end) {
        if (weatherHistorySubscription == null || weatherHistorySubscription.isUnsubscribed()) {
            weatherHistorySubject = ReplaySubject.create();

            weatherHistorySubscription = Observable.concat(memoryWeatherHistory(), diskWeatherHistory(), networkWeatherHistory(city, start, end))
                    .first(entity -> entity != null && isUpToDate(entity))
                    .subscribe(weatherHistorySubject);
        }

        return weatherHistorySubject.asObservable();

    }

    @Override
    public Observable<WeatherHistory> loadWeatherHistory(long lat, long lon, long start, long end) {
        if (weatherHistorySubscription == null || weatherHistorySubscription.isUnsubscribed()) {
            weatherHistorySubject = ReplaySubject.create();

            weatherHistorySubscription = Observable.concat(memoryWeatherHistory(), diskWeatherHistory(), networkWeatherHistory(lat, lon, start, end))
                    .first(entity -> entity != null && isUpToDate(entity))
                    .subscribe(weatherHistorySubject);
        }

        return weatherHistorySubject.asObservable();

    }


    @Override
    public void onDestroy() {
        mNetworkService = null;
        mDiskCache = null;
        mScheduler = null;
        mClock = null;
    }


    private Observable<WeatherMix> networkWeather(String city) {
        return mNetworkService.loadWeather(city)
                .doOnNext(entity -> memoryCacheWeather = entity)
                .flatMap(entity -> mDiskCache.saveWeather(entity).map(__ -> entity))
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread());
    }

    private Observable<WeatherMix> networkWeather(long lat, long lon) {
        return mNetworkService.loadWeather(lat, lon)
                .doOnNext(entity -> memoryCacheWeather = entity)
                .flatMap(entity -> mDiskCache.saveWeather(entity).map(__ -> entity))
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread());
    }


    private Observable<WeatherHistory> networkWeatherHistory(String city, long start, long end) {
        return mNetworkService.loadWeatherHistory(city, start, end)
                .doOnNext(entity -> memoryCacheWeatherHistory = entity)
                .flatMap(entity -> mDiskCache.saveWeatherHistory(entity).map(__ -> entity))
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread());
    }

    private Observable<WeatherHistory> networkWeatherHistory(long lat, long lon, long start, long end) {
        return mNetworkService.loadWeatherHistory(lat, lon, start, end)
                .doOnNext(entity -> memoryCacheWeatherHistory = entity)
                .flatMap(entity -> mDiskCache.saveWeatherHistory(entity).map(__ -> entity))
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread());
    }

    private Observable<WeatherHistory> diskWeatherHistory() {
        return mDiskCache.getWeatherHistory()
                .doOnNext(entity -> memoryCacheWeatherHistory = entity)
                .subscribeOn(mScheduler.backgroundThread())
                .observeOn(mScheduler.mainThread());
    }

    private Observable<WeatherHistory> memoryWeatherHistory() {
        return Observable.just(memoryCacheWeatherHistory);
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
        memoryCacheWeatherHistory = null;
    }

    private boolean isUpToDate(WeatherMix entity) {
        return mClock.millis() / 1000 - entity.getWeatherCurrent().getDt() < Constants.STALE_MS;
    }

    private boolean isUpToDate(WeatherHistory entity) {
        return mClock.millis() / 1000 - entity.getList().get(0).getDt() < Constants.STALE_MS;
    }

    private boolean isSameCity(String city, WeatherMix entity) {
        return entity.getWeatherCurrent().getName().equalsIgnoreCase(city);
    }

    private boolean isSameCity(long lat, long lon, WeatherMix entity) {
        return entity.getWeatherCurrent().getCoord().getLat() == lat && entity.getWeatherCurrent().getCoord().getLon() == lon;
    }


}
