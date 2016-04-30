package com.mirhoseini.weatherapp.core.presenter;


import com.mirhoseini.weatherapp.core.model.MainInteractorImpl;
import com.mirhoseini.weatherapp.core.network.INetwork;
import com.mirhoseini.weatherapp.core.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.network.model.WeatherMix;
import com.mirhoseini.weatherapp.core.service.WeatherService;
import com.mirhoseini.weatherapp.core.utils.IScheduler;
import com.mirhoseini.weatherapp.core.view.IViewMain;
import com.mirhoseini.weatherapp.core.model.MainInteractor;
import com.mirhoseini.weatherapp.core.model.OnMainNetworkFinishedListener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mohsen on 30/04/16.
 */
public class Presenter implements IPresenter, OnMainNetworkFinishedListener {

    private static boolean sDoubleBackToExitPressedOnce;

    MainInteractor mMainInteractor;
    private IViewMain mView;

    public Presenter(WeatherService weatherService, IScheduler scheduler, INetwork network, IViewMain mView) {
        this.mView = mView;

        mMainInteractor = new MainInteractorImpl(network);

        sDoubleBackToExitPressedOnce = false;


    }


    @Override
    public void onResume() {

        sDoubleBackToExitPressedOnce = false;

    }

    @Override
    public void onDestroy() {
        //delete all references with UI destruction
        mView = null;
        mMainInteractor.onDestroy();
        mMainInteractor = null;
    }

    @Override
    public boolean onBackPressed() {
        // check for double back press to exit
        if (sDoubleBackToExitPressedOnce) {
            if (mView != null) {
                mView.exit();
            }
            return false;
        }
        sDoubleBackToExitPressedOnce = true;

        if (mView != null) {
            mView.showExitMessage();
        }

        Timer t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                sDoubleBackToExitPressedOnce = false;
            }
        }, 2500);

        return false;
    }

    @Override
    public void loadWeather(String city, boolean isConnected) {

        if (mView != null) {
            mView.showProgress();
            mView.showProgressMessage("Loading City Weather...");
        }

        //load data from network
        if (isConnected) {

            mMainInteractor.loadWeather(city, this);

        } else { // network connection is required

            if (mView != null) {
                mView.showConnectionError();
                mView.hideProgress();
            }

        }
    }

    @Override
    public void loadWeather(long lat, long lon, boolean isConnected) {

        if (mView != null) {
            mView.showProgress();
            mView.showProgressMessage("Loading Current Location Weather...");
        }

        //load data from network
        if (isConnected) {

            mMainInteractor.loadWeather(lat, lon, this);

        } else { // network connection is required

            if (mView != null) {
                mView.showConnectionError();
                mView.hideProgress();
            }

        }
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

        //load data from network
        if (isConnected) {

            mMainInteractor.loadWeatherHistory(city, start, end, this);

        } else { // network connection is required

            if (mView != null) {
                mView.showConnectionError();
                mView.hideProgress();
            }

        }
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

        //load data from network
        if (isConnected) {

            mMainInteractor.loadWeatherHistory(lat, lon, start, end, this);

        } else { // network connection is required

            if (mView != null) {
                mView.showConnectionError();
                mView.hideProgress();
            }

        }
    }

    @Override
    public void onNetworkSuccess(WeatherMix weatherMix) {

        if (mMainInteractor != null) {//check if object is not destroyed

            if (mView != null) {
                mView.setWeatherValues(weatherMix);
            }

            mView.hideProgress();

        }
    }

    @Override
    public void onNetworkError(Throwable throwable) {

        if (mView != null) {
            mView.showToastMessage(throwable.getMessage());
            mView.hideProgress();
            mView.showRetryMessage();
        }

    }

    @Override
    public void onNetworkHistorySuccess(WeatherHistory weatherHistory) {
        if (mMainInteractor != null) {//check if object is not destroyed

            if (mView != null) {
                mView.setWeatherHistoryValues(weatherHistory);
            }

            mView.hideProgress();

        }
    }

}
