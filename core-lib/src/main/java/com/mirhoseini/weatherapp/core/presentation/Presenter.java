package com.mirhoseini.weatherapp.core.presentation;


import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.model.IInteractor;
import com.mirhoseini.weatherapp.core.model.WeatherInteractor;
import com.mirhoseini.weatherapp.core.service.INetworkService;
import com.mirhoseini.weatherapp.core.utils.ICacher;
import com.mirhoseini.weatherapp.core.utils.IScheduler;
import com.mirhoseini.weatherapp.core.view.IViewMain;

import java.util.Calendar;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 30/04/16.
 */
public class Presenter implements IPresenter {


    IInteractor mInteractor;
    private IViewMain mView;

    private Subscription subscription = Subscriptions.empty();

    public Presenter(ICacher cacher, INetworkService networkService, IScheduler scheduler) {

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
        //delete all references with UI destruction
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
                            mView.showToastMessage(throwable.getMessage());
                            mView.showRetryMessage();
                            mView.hideProgress();
                        }
                    } else {

                        if (mView != null) {
                            mView.showConnectionError();
                            mView.hideProgress();
                        }
                    }
                },
                () -> {
                    if (mView != null) {
                        mView.hideProgress();
                    }
                });

        //load data from network
//        if (isConnected) {
//
//
//            mInteractor.loadWeather(city, this);
//
//        } else { // network connection is required
//
//            if (mView != null) {
//                mView.showConnectionError();
//                mView.hideProgress();
//            }
//
//        }
    }

    @Override
    public void loadWeather(long lat, long lon, boolean isConnected) {

        if (mView != null) {
            mView.showProgress();
            mView.showProgressMessage("Loading Current Location Weather...");
        }

        subscription = mInteractor.loadWeather(lat, lon).subscribe(
                weatherMix -> {
                    if (mView != null) {
                        mView.setWeatherValues(weatherMix);
                    }
                },
                throwable -> {
                    if (isConnected) {

                        if (mView != null) {
                            mView.showToastMessage(throwable.getMessage());
                            mView.showRetryMessage();
                            mView.hideProgress();
                        }
                    } else {

                        if (mView != null) {
                            mView.showConnectionError();
                            mView.hideProgress();
                        }
                    }
                },
                () -> {
                    if (mView != null) {
                        mView.hideProgress();
                    }
                });

//        //load data from network
//        if (isConnected) {
//
//            mInteractor.loadWeather(lat, lon, this);
//
//        } else { // network connection is required
//
//            if (mView != null) {
//                mView.showConnectionError();
//                mView.hideProgress();
//            }
//
//        }
    }

    @Override
    public void loadWeatherHistory(String city, boolean isConnected) {

        Calendar calendar = Calendar.getInstance();
        long end = calendar.getTime().getTime();

        calendar.add(Calendar.DATE, -7);
        long start = calendar.getTime().getTime();

        if (mView != null) {
            mView.showProgress();
            mView.showProgressMessage("Loading City Weather History...");
        }

        subscription = mInteractor.loadWeatherHistory(city, start, end).subscribe(
                weatherHistory -> {
                    if (mView != null) {
                        mView.setWeatherHistoryValues(weatherHistory);
                    }
                },
                throwable -> {
                    if (isConnected) {

                        if (mView != null) {
                            mView.showToastMessage(throwable.getMessage());
                            mView.showRetryMessage();
                            mView.hideProgress();
                        }
                    } else {

                        if (mView != null) {
                            mView.showConnectionError();
                            mView.hideProgress();
                        }
                    }
                },
                () -> {
                    if (mView != null) {
                        mView.hideProgress();
                    }
                });


        //load data from network
//        if (isConnected) {
//
//            mInteractor.loadWeatherHistory(city, start, end, this);
//
//        } else { // network connection is required
//
//            if (mView != null) {
//                mView.showConnectionError();
//                mView.hideProgress();
//            }
//
//        }
    }

    @Override
    public void loadWeatherHostory(long lat, long lon, boolean isConnected) {

        Calendar calendar = Calendar.getInstance();
        long end = calendar.getTime().getTime();

        calendar.add(Calendar.DATE, -7);
        long start = calendar.getTime().getTime();

        if (mView != null) {
            mView.showProgress();
            mView.showProgressMessage("Loading Current Location Weather History...");
        }

        subscription = mInteractor.loadWeatherHistory(lat, lon, start, end).subscribe(
                weatherHistory -> {
                    if (mView != null) {
                        mView.setWeatherHistoryValues(weatherHistory);
                    }
                },
                throwable -> {
                    if (isConnected) {

                        if (mView != null) {
                            mView.showToastMessage(throwable.getMessage());
                            mView.showRetryMessage();
                            mView.hideProgress();
                        }
                    } else {

                        if (mView != null) {
                            mView.showConnectionError();
                            mView.hideProgress();
                        }
                    }
                },
                () -> {
                    if (mView != null) {
                        mView.hideProgress();
                    }
                });

        //load data from network
//        if (isConnected) {
//
//            mInteractor.loadWeatherHistory(lat, lon, start, end, this);
//
//        } else { // network connection is required
//
//            if (mView != null) {
//                mView.showConnectionError();
//                mView.hideProgress();
//            }
//
//        }
    }

//    @Override
//    public void onNetworkSuccess(WeatherMix weatherMix) {
//
//        if (mInteractor != null) {//check if object is not destroyed
//
//            if (mView != null) {
//                mView.setWeatherValues(weatherMix);
//            }
//
//            mView.hideProgress();
//
//        }
//    }
//
//    @Override
//    public void onNetworkError(Throwable throwable) {
//
//        if (mView != null) {
//            mView.showToastMessage(throwable.getMessage());
//            mView.hideProgress();
//            mView.showRetryMessage();
//        }
//
//    }
//
//    @Override
//    public void onNetworkHistorySuccess(WeatherHistory weatherHistory) {
//        if (mInteractor != null) {//check if object is not destroyed
//
//            if (mView != null) {
//                mView.setWeatherHistoryValues(weatherHistory);
//            }
//
//            mView.hideProgress();
//
//        }
//    }

}
