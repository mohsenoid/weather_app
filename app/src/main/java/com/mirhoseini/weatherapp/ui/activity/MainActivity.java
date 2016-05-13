package com.mirhoseini.weatherapp.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.utils.Utils;
import com.mirhoseini.weatherapp.BaseActivity;
import com.mirhoseini.weatherapp.BuildConfig;
import com.mirhoseini.weatherapp.R;
import com.mirhoseini.weatherapp.WeatherApplication;
import com.mirhoseini.weatherapp.core.presentation.IPresenter;
import com.mirhoseini.weatherapp.core.presentation.WeatherPresenter;
import com.mirhoseini.weatherapp.core.service.INetworkService;
import com.mirhoseini.weatherapp.core.service.WeatherNetworkService;
import com.mirhoseini.weatherapp.core.service.model.WeatherHistory;
import com.mirhoseini.weatherapp.core.service.model.WeatherMix;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.core.utils.ICacher;
import com.mirhoseini.weatherapp.core.utils.IScheduler;
import com.mirhoseini.weatherapp.core.view.IViewMain;
import com.mirhoseini.weatherapp.ui.fragment.CurrentFragment;
import com.mirhoseini.weatherapp.ui.fragment.ForecastFragment;
import com.mirhoseini.weatherapp.ui.fragment.HistoryFragment;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import timber.log.Timber;

/**
 * Created by Mohsen on 30/04/16.
 */
public class MainActivity extends BaseActivity implements IViewMain, ForecastFragment.OnCurrentFragmentInteractionListener {

    static boolean sDoubleBackToExitPressedOnce;
    Context mContext;

    AlertDialog mInternetConnectionDialog;

    //injecting dependencies via Dagger
    @Inject
    IScheduler mScheduler;
    @Inject
    ICacher mCacher;
    @Inject
    INetworkService mNetworkService;
    @Inject
    IPresenter mPresenter;

    //injecting views via Butterknife
    @BindView(R.id.progress_container)
    ViewGroup mProgressContainer;
    @BindView(R.id.progress_message)
    TextView mProgressMessage;
    @BindView(R.id.city_edittext)
    EditText mCityEditText;
    @BindView(R.id.fragment_container)
    ViewGroup mFragmentContainer;


    @OnEditorAction(R.id.city_edittext)
    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
        if (action == EditorInfo.IME_ACTION_GO || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            submit(textView);
        }
        return false;
    }

    @OnClick(R.id.go_button)
    public void submit(View view) {
        Answers.getInstance().logCustom(new CustomEvent("Pressed Go Button").putCustomAttribute("City Name", mCityEditText.getText().toString()));

        //hide keyboard for better UX
        Utils.hideKeyboard(mContext, mCityEditText);

        getWeatherData(mCityEditText.getText().toString());
    }

    private void getWeatherData(String city) {
        if (city == null || city.isEmpty()) {
            //current user location can be loaded using GPS data for next version
            hideProgress();
        } else {
            //load city weather data
            mPresenter.loadWeather(city, Utils.isConnected(mContext));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        WeatherApplication app = (WeatherApplication) getApplication();
        app.getComponent().inject(this);

        mPresenter.setView(this);

        // binding Views using ButterKnife
        ButterKnife.bind(this);

        Timber.d("Activity Created");


        //load last city from Memory using savedInstanceState or DiskCache using sharedPreferences
        String lastCity = "";

        if (savedInstanceState == null) { //load lastCity from sharePreferences
            lastCity = AppSettings.getString(mContext, Constants.KEY_LAST_CITY, Constants.CITY_DEFAULT_VALUE);
        } else { //load lastCity from saved state before UI change
            lastCity = savedInstanceState.getString(Constants.KEY_LAST_CITY);
        }

        mCityEditText.setText(lastCity);

        getWeatherData(lastCity);
    }

    @Override
    protected void onResume() {
        Timber.d("Activity Resumed");
        super.onResume();

        sDoubleBackToExitPressedOnce = false;

        // dismiss no internet connection dialog in case of getting back from setting and connection fixed
        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();
    }


    @Override
    protected void onDestroy() {
        Timber.d("Activity Destroyed");

        // call destroy to remove references to objects
        mPresenter.onDestroy();
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
    public void setWeatherValues(WeatherMix weatherMix) {
        Timber.d("Setting Weather: %s", weatherMix.toString());

        // save last loaded data city name to disk cache for better UX
        saveLastCity(weatherMix.getWeatherCurrent().getName());

        mFragmentContainer.setVisibility(View.VISIBLE);

        // load data to corresponding fragment
        CurrentFragment currentFragment = CurrentFragment.newInstance(weatherMix.getWeatherCurrent());
        ForecastFragment forecastFragment = ForecastFragment.newInstance(weatherMix.getWeatherForecast());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.current_fragment, currentFragment, "current");
        fragmentTransaction.replace(R.id.forecast_fragment, forecastFragment, "forecast");
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void setWeatherHistoryValues(WeatherHistory weatherHistory) {
        Timber.d("Setting Weather History: %s", weatherHistory.toString());

        mFragmentContainer.setVisibility(View.VISIBLE);

        HistoryFragment historyFragment = HistoryFragment.newInstance(weatherHistory);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.current_fragment, historyFragment).commit();
    }

    // save user last city
    private void saveLastCity(String city) {
        Timber.d("Saving Last City");

        AppSettings.setValue(mContext, Constants.KEY_LAST_CITY, city);
    }

    @Override
    public void showToastMessage(String message) {
        Timber.d("Showing Toast Message: %s", message);

        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
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
    public void showExitMessage() {
        Timber.d("Showing Exit Message");

        Toast.makeText(mContext, R.string.msg_exit, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionError() {
        Timber.d("Showing Connection Error Message");

        if (mInternetConnectionDialog != null)
            mInternetConnectionDialog.dismiss();

        mInternetConnectionDialog = Utils.showNoInternetConnectionDialog(mContext, false);
    }

    @Override
    public void showRetryMessage() {
        Timber.d("Showing Retry Message");

        Snackbar.make(mCityEditText, R.string.retry_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.load_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getWeatherData(mCityEditText.getText().toString());
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
    }


    @Override
    public void onBackPressed() {
        Timber.d("Activity Back Pressed");

        // check for double back press to exit
        if (sDoubleBackToExitPressedOnce) {
            Timber.d("Exiting");

            Utils.exit(mContext);
        } else {

            sDoubleBackToExitPressedOnce = true;

            showExitMessage();

            Timer t = new Timer();
            t.schedule(new TimerTask() {

                @Override
                public void run() {
                    sDoubleBackToExitPressedOnce = false;
                }

            }, 2500);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.d("Activity Saving Instance State");

        //save TimeSpan selected by user before data loaded and saved to SharedPreferences
        outState.putString(Constants.KEY_LAST_CITY, mCityEditText.getText().toString());

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onLoadHistory(String city) {
        // load city weather history, call from forecast fragment
        mPresenter.loadWeatherHistory(city, Utils.isConnected(mContext));
    }
}