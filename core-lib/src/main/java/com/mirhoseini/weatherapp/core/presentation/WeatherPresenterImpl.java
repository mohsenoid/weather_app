package com.mirhoseini.weatherapp.core.presentation;


import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.model.WeatherInteractor;
import com.mirhoseini.weatherapp.core.model.WeatherInteractorImpl;
import com.mirhoseini.weatherapp.core.service.WeatherService;
import com.mirhoseini.weatherapp.core.utils.CacheProvider;
import com.mirhoseini.weatherapp.core.utils.SchedulerProvider;
import com.mirhoseini.weatherapp.core.view.MainView;

import java.util.Calendar;
import java.util.NoSuchElementException;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherPresenterImpl implements WeatherPresenter,LifecyclePresenter {


    WeatherInteractorImpl weatherInteractor;
    private MainView mainView;

    private Subscription subscription = Subscriptions.empty();

    public WeatherPresenterImpl(CacheProvider cacheProvider, WeatherService weatherService, SchedulerProvider schedulerProvider) {
        weatherInteractor = new WeatherInteractorImpl(cacheProvider, weatherService, schedulerProvider, Clock.REAL);
    }

    public MainView getView() {
        return mainView;
    }

    @Override
    public void setView(MainView view) {

        this.mainView = view;

        if (view == null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        //delete all references in case of UI destruction
        mainView = null;
        weatherInteractor.onDestroy();
        weatherInteractor = null;
    }

    @Override
    public void loadWeather(String city, boolean isConnected) {

        if (mainView != null) {
            mainView.showProgress();
            mainView.showProgressMessage("Loading City Weather...");
        }

        subscription = weatherInteractor.loadWeather(city).subscribe(
                weatherMix -> {
                    if (mainView != null) {
                        mainView.setWeatherValues(weatherMix);
                    }
                },
                throwable -> {
                    if (isConnected) {
                        if (mainView != null) {
                            if (throwable.getClass().equals(NoSuchElementException.class)) {
                                mainView.showToastMessage("City not found!!!");
                            } else {
                                mainView.showToastMessage(throwable.getMessage());
                                mainView.showRetryMessage();
                            }
                        }
                    } else {
                        if (mainView != null) {
                            mainView.showConnectionError();
                        }
                    }
                    mainView.hideProgress();
                },
                () -> {
                    if (mainView != null) {
                        mainView.hideProgress();
                    }
                });

    }

    @Override
    public void loadWeatherHistory(String city, boolean isConnected) {

        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTime().getTime();

        // last 7-Day
        calendar.add(Calendar.DATE, -7);
        long end = calendar.getTime().getTime();

        if (mainView != null) {
            mainView.showProgress();
            mainView.showProgressMessage("Loading City Weather History...");
        }

        subscription = weatherInteractor.loadWeatherHistory(city, start / 1000, end / 100).subscribe(
                weatherHistory -> {
                    if (mainView != null) {
                        mainView.setWeatherHistoryValues(weatherHistory);
                    }
                },
                throwable -> {
                    if (isConnected) {
                        if (throwable.getClass().equals(NoSuchElementException.class)) {
                            mainView.showToastMessage("City not found!!!");
                        } else {
                            mainView.showToastMessage(throwable.getMessage());
                            mainView.showRetryMessage();
                        }
                    } else {
                        if (mainView != null) {
                            mainView.showConnectionError();
                        }
                    }
                    mainView.hideProgress();
                },
                () -> {
                    if (mainView != null) {
                        mainView.hideProgress();
                    }
                });
    }
}
