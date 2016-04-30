package com.mirhoseini.weatherapp.ui.main.presenter;


import android.support.v4.util.Pair;

import com.mirhoseini.weatherapp.network.model.WeatherCurrent;
import com.mirhoseini.weatherapp.network.model.WeatherForecast;
import com.mirhoseini.weatherapp.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.ui.main.model.MainInteractor;
import com.mirhoseini.weatherapp.ui.main.model.MainInteractorImpl;
import com.mirhoseini.weatherapp.ui.main.model.OnMainNetworkFinishedListener;
import com.mirhoseini.weatherapp.ui.main.view.MainView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;


/**
 * Created by Mohsen on 30/04/16.
 */
public class MainPresenterImpl implements MainPresenter, OnMainNetworkFinishedListener {
    public static final String TAG = MainPresenterImpl.class.getSimpleName();

    private static boolean sDoubleBackToExitPressedOnce;

    MainInteractor mMainInteractor;
    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;

        mMainInteractor = new MainInteractorImpl();

        Timber.tag(TAG);

        sDoubleBackToExitPressedOnce = false;
    }

    @Override
    public void onResume() {

        sDoubleBackToExitPressedOnce = false;

    }

    @Override
    public void onDestroy() {
        //delete all references with UI destruction
        mMainView = null;
        mMainInteractor.onDestroy();
        mMainInteractor = null;
    }

    @Override
    public boolean onBackPressed() {
        // check for double back press to exit
        if (sDoubleBackToExitPressedOnce) {
            if (mMainView != null) {
                mMainView.exit();
            }
            return false;
        }
        sDoubleBackToExitPressedOnce = true;

        if (mMainView != null) {
            mMainView.showExitMessage();
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
        Timber.d("Loading City Weather");

        if (mMainView != null) {
            mMainView.showProgress();
            mMainView.showProgressMessage("Loading City Weather...");
        }

        //load data from network
        if (isConnected) {

            mMainInteractor.loadWeather(city, this);

        } else { // network connection is required

            if (mMainView != null) {
                mMainView.showConnectionError();
                mMainView.hideProgress();
            }

        }
    }

    @Override
    public void loadWeather(long lat, long lon, boolean isConnected) {
        Timber.d("Loading Location Weather");

        if (mMainView != null) {
            mMainView.showProgress();
            mMainView.showProgressMessage("Loading Current Location Weather...");
        }

        //load data from network
        if (isConnected) {

            mMainInteractor.loadWeather(lat, lon, this);

        } else { // network connection is required

            if (mMainView != null) {
                mMainView.showConnectionError();
                mMainView.hideProgress();
            }

        }
    }

    @Override
    public void loadWeatherHistory(String city, boolean isConnected) {
        Timber.d("Loading City Weather History");

        Calendar calendar = Calendar.getInstance();
        long end = calendar.getTime().getTime();

        calendar.add(Calendar.DATE, -7);
        long start = calendar.getTime().getTime();

        if (mMainView != null) {
            mMainView.showProgress();
            mMainView.showProgressMessage("Loading City Weather History...");
        }

        //load data from network
        if (isConnected) {

            mMainInteractor.loadWeatherHistory(city, start, end, this);

        } else { // network connection is required

            if (mMainView != null) {
                mMainView.showConnectionError();
                mMainView.hideProgress();
            }

        }
    }

    @Override
    public void loadWeatherHostory(long lat, long lon, boolean isConnected) {
        Timber.d("Loading Location Weather History");

        Calendar calendar = Calendar.getInstance();
        long end = calendar.getTime().getTime();

        calendar.add(Calendar.DATE, -7);
        long start = calendar.getTime().getTime();

        if (mMainView != null) {
            mMainView.showProgress();
            mMainView.showProgressMessage("Loading Current Location Weather History...");
        }

        //load data from network
        if (isConnected) {

            mMainInteractor.loadWeatherHistory(lat, lon, start, end, this);

        } else { // network connection is required

            if (mMainView != null) {
                mMainView.showConnectionError();
                mMainView.hideProgress();
            }

        }
    }

    @Override
    public void onNetworkSuccess(Pair<WeatherCurrent, WeatherForecast> weatherPair) {

        if (mMainInteractor != null) {//check if object is not destroyed

            if (mMainView != null) {
                mMainView.setWeatherValues(weatherPair);
            }

            mMainView.hideProgress();

        }
    }

    @Override
    public void onNetworkError(Throwable throwable) {

        if (mMainView != null) {
            mMainView.showToastMessage(throwable.getMessage());
            mMainView.hideProgress();
            mMainView.showRetryMessage();
        }

    }

    @Override
    public void onNetworkHistorySuccess(WeatherHistory weatherHistory) {
        if (mMainInteractor != null) {//check if object is not destroyed

            if (mMainView != null) {
                mMainView.setWeatherHistoryValues(weatherHistory);
            }

            mMainView.hideProgress();

        }
    }

}
