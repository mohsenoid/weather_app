package com.mirhoseini.weatherapp.core.presentation;


import com.mirhoseini.weatherapp.core.di.WeatherScope;
import com.mirhoseini.weatherapp.core.model.WeatherInteractor;
import com.mirhoseini.weatherapp.core.view.MainView;

import java.util.Calendar;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 30/04/16.
 */
@WeatherScope
public class WeatherPresenterImpl implements WeatherPresenter, LifecyclePresenter {

    WeatherInteractor interactor;
    private MainView view;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public WeatherPresenterImpl(WeatherInteractor interactor) {
        this.interactor = interactor;
    }

    public void setView(MainView view) {
        this.view = view;
    }

    @Override
    public void onResume() {
//        if (view != null) {
//            view.hideProgress();
//        }
    }

    @Override
    public void onPause() {
//        subscription.unsubscribe();
    }

    @Override
    public void onDestroy() {
        //delete all references in case of UI destruction
        view = null;
        interactor.onDestroy();
        interactor = null;
    }

    @Override
    public void loadWeather(String city, boolean isConnected) {

        if (view != null) {
            view.showProgress();
            view.showProgressMessage("Loading City Weather...");
        }

        subscription = interactor.loadWeather(city).subscribe(
                weatherMix -> {
                    if (view != null) {
                        view.setWeatherValues(weatherMix);
                    }
                },
                throwable -> {
                    if (isConnected) {
                        if (view != null) {
                            if (throwable.getClass().equals(NoSuchElementException.class)) {
                                view.showToastMessage("City not found!!!");
                            } else {
//                                view.showToastMessage(throwable.getMessage());
                                view.showRetryMessage();
                            }
                        }
                    } else {
                        if (view != null) {
                            view.showConnectionError();
                        }
                    }

                    if (view != null) {
                        view.hideProgress();
                    }
                },
                () -> {
                    if (view != null) {
                        view.hideProgress();
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

        if (view != null) {
            view.showProgress();
            view.showProgressMessage("Loading City Weather History...");
        }

        subscription = interactor.loadWeatherHistory(city, start / 1000, end / 100).subscribe(
                weatherHistory -> {
                    if (view != null) {
                        view.setWeatherHistoryValues(weatherHistory);
                    }
                },
                throwable -> {
                    if (isConnected) {
                        if (view != null) {
                            if (throwable.getClass().equals(NoSuchElementException.class)) {
                                view.showToastMessage("City not found!!!");
                            } else {
                                view.showToastMessage(throwable.getMessage());
                                view.showRetryMessage();
                            }
                        }
                    } else {
                        if (view != null) {
                            view.showConnectionError();
                        }
                    }

                    if (view != null) {
                        view.hideProgress();
                    }
                },
                () -> {
                    if (view != null) {
                        view.hideProgress();
                    }
                });
    }
}
