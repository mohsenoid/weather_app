package com.mirhoseini.weatherapp.ui.main.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.utils.Utils;
import com.mirhoseini.weatherapp.R;
import com.mirhoseini.weatherapp.network.model.WeatherCurrent;
import com.mirhoseini.weatherapp.network.model.WeatherForecast;
import com.mirhoseini.weatherapp.network.model.WeatherHistory;
import com.mirhoseini.weatherapp.ui.BaseActivity;
import com.mirhoseini.weatherapp.ui.main.presenter.MainPresenter;
import com.mirhoseini.weatherapp.ui.main.presenter.MainPresenterImpl;
import com.mirhoseini.weatherapp.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by Mohsen on 30/04/16.
 */
public class MainActivity extends BaseActivity implements MainView {
    public static final String TAG = MainActivity.class.getSimpleName();

    MainPresenter mMainPresenter;

    AlertDialog mInternetConnectionDialog;

    @BindView(R.id.progress_container)
    ViewGroup mProgressContainer;
    @BindView(R.id.progress_message)
    TextView mProgressMessage;
    @BindView(R.id.city_edittext)
    EditText mCityEditText;
    @BindView(R.id.fragment_container)
    ViewGroup mFragmentContainer;

    @OnClick(R.id.go_button)
    public void submit(View view) {
        if (mCityEditText.getText().toString().isEmpty()) {
//            mCityEditText.setError(getString(R.string.city_error));
//            mCityEditText.requestFocus();

            //TODO: load current location weather
            hideProgress();

        } else {
            mMainPresenter.loadWeather(mCityEditText.getText().toString(), Utils.isConnected(mContext));
        }

        saveLastCity(mCityEditText.getText().toString());
        Utils.hideKeyboard(this, mCityEditText);
    }

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mMainPresenter = new MainPresenterImpl(this);

        // binding Views using ButterKnife library
        ButterKnife.bind(this);

        Timber.tag(TAG);
        Timber.d("Activity Created");

        String lastCity = "";

        if (savedInstanceState == null) { //load lastTimeSpan from SharePreferences
            lastCity = AppSettings.getString(this, Constants.LAST_CITY, "");
        } else { //load lastTimeSpan from saved state before UI change
            lastCity = savedInstanceState.getString(Constants.LAST_CITY);
        }

        mCityEditText.setText(lastCity);
    }

    @Override
    protected void onResume() {
        Timber.d("Activity Resumed");

        super.onResume();
        mMainPresenter.onResume();

        // dismiss no internet connection dialog in case of connection fixed
        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();

        if (mCityEditText.getText().toString().isEmpty()) {
            //TODO: load from location
            hideProgress();
        } else {
            mMainPresenter.loadWeather(mCityEditText.getText().toString(), Utils.isConnected(this));
        }
    }


    @Override
    protected void onDestroy() {
        Timber.d("Activity Destroyed");

        mMainPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        Timber.d("Showing Progress");

        mProgressContainer.setVisibility(View.VISIBLE);
        mFragmentContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        Timber.d("Hiding Progress");

        mProgressContainer.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setWeatherValues(Pair<WeatherCurrent, WeatherForecast> weatherPair) {
        Timber.d("Setting Weather: %s", weatherPair.toString());

        mFragmentContainer.setVisibility(View.VISIBLE);

        //used jjoe64 graphview before preparing my own Graph View
            /*
            DataPoint[] data = new DataPoint[items.size()];
            for (int i = 0; i < items.size(); i++) {
                data[i] = new DataPoint(new Date(items.get(i).getX() * 1000), items.get(i).getY());
            }

            mGraph.removeAllSeries();

            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data);

            mGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(mContext));
            mGraph.getGridLabelRenderer().setNumHorizontalLabels(4);
            mGraph.getViewport().setScalable(true);
            mGraph.getViewport().setScrollable(true);
            mGraph.addSeries(series);
            */

    }

    @Override
    public void setWeatherHistoryValues(WeatherHistory weatherHistory) {

    }

    // save user last city
    private void saveLastCity(String city) {
        Timber.d("Saving Last City");

        AppSettings.setValue(this, Constants.LAST_CITY, city);
    }

    @Override
    public void showToastMessage(String message) {
        Timber.d("Showing Toast Message: %s", message);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressMessage(String message) {
        Timber.d("Showing Progress Message: %s", message);

        mProgressMessage.setText(message);
    }

    @Override
    public void showOfflineMessage() {
        Timber.d("Showing Offline Message");

        Snackbar.make(mCityEditText, R.string.offline_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.go_online, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(
                                Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setActionTextColor(Color.GREEN)
                .show();
    }

    @Override
    public void exit() {
        Timber.d("Exiting");

        Utils.exit(this);
    }

    @Override
    public void showExitMessage() {
        Timber.d("Showing Exit Message");

        Toast.makeText(this, R.string.msg_exit, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionError() {
        Timber.d("Showing Connection Error Message");

        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();

        mInternetConnectionDialog = Utils.showNoInternetConnectionDialog(this, false);
    }

    @Override
    public void showRetryMessage() {
        Timber.d("Showing Retry Message");

        Snackbar.make(mCityEditText, R.string.retry_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.load_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCityEditText.getText().toString().isEmpty()) {
                            //TODO: load from location
                            hideProgress();
                        } else {
                            mMainPresenter.loadWeather(mCityEditText.getText().toString(), Utils.isConnected(mContext));
                        }
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
    }


    @Override
    public void onBackPressed() {
        Timber.d("Activity Back Pressed");

        if (mMainPresenter.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.d("Activity Saving Instance State");

        super.onSaveInstanceState(outState);

        //save TimeSpan selected by user before data loaded and saved to SharedPreferences
        outState.putString(Constants.LAST_CITY, mCityEditText.getText().toString());
    }


}