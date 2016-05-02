package com.mirhoseini.weatherapp.core.presentation;


import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.model.IInteractor;
import com.mirhoseini.weatherapp.core.model.WeatherInteractor;
import com.mirhoseini.weatherapp.core.service.INetworkService;
import com.mirhoseini.weatherapp.core.utils.ICacher;
import com.mirhoseini.weatherapp.core.utils.IScheduler;
import com.mirhoseini.weatherapp.core.view.IViewMain;

import java.util.Calendar;
import java.util.NoSuchElementException;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherPresenter implements IPresenter {


    IInteractor mInteractor;
    private IViewMain mView;

    private Subscription subscription = Subscriptions.empty();

    public WeatherPresenter(ICacher cacher, INetworkService networkService, IScheduler scheduler) {

        mInteractor = new WeatherInteractor(cacher, networkService, scheduler, Clock.REAL);

    }

    public IViewMain getView() {
        return mView;
    }

    @Override
    public void setView(IViewMain view) {

        this.mView = view;

        if (view == null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        //delete all references in case of UI destruction
        mView = null;
        mInteractor.onDestroy();
        mInteractor = null;
    }

    @Override
    public void loadWeather(String city, boolean isConnected) {

        if (mView != null) {
            mView.showProgress();
            mView.showProgressMessage("Loading City Weather...");
        }

        subscription = mInteractor.loadWeather(city).subscribe(
                weatherMix -> {
                    if (mView != null) {
                        mView.setWeatherValues(weatherMix);
                    }
                },
                throwable -> {
                    if (isConnected) {
                        if (mView != null) {
                            if (throwable.getClass().equals(NoSuchElementException.class)) {
                                mView.showToastMessage("City not found!!!");
                            } else {
                                mView.showToastMessage(throwable.getMessage());
                                mView.showRetryMessage();
                            }
                        }
                    } else {
                        if (mView != null) {
                            mView.showConnectionError();
                        }
                    }
                    mView.hideProgress();
                },
                () -> {
                    if (mView != null) {
                        mView.hideProgress();
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

        if (mView != null) {
            mView.showProgress();
            mView.showProgressMessage("Loading City Weather History...");
        }

        subscription = mInteractor.loadWeatherHistory(city, start / 1000, end / 100).subscribe(
                weatherHistory -> {
                    if (mView != null) {
                        mView.setWeatherHistoryValues(weatherHistory);
                    }
                },
                throwable -> {
                    if (isConnected) {
                        if (throwable.getClass().equals(NoSuchElementException.class)) {
                            mView.showToastMessage("City not found!!!");
                        } else {
                            mView.showToastMessage(throwable.getMessage());
                            mView.showRetryMessage();
                        }
                    } else {
                        if (mView != null) {
                            mView.showConnectionError();
                        }
                    }
                    mView.hideProgress();
                },
                () -> {
                    if (mView != null) {
                        mView.hideProgress();
                    }
                });
    }
}
