package com.mirhoseini.weatherapp.ui.activity;

import android.app.AlertDialog;
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
import com.mirhoseini.weatherapp.R;
import com.mirhoseini.weatherapp.core.presentation.WeatherPresenter;
import com.mirhoseini.weatherapp.core.service.WeatherApiService;
import com.mirhoseini.weatherapp.core.util.CacheProvider;
import com.mirhoseini.weatherapp.core.util.Constants;
import com.mirhoseini.weatherapp.core.util.SchedulerProvider;
import com.mirhoseini.weatherapp.core.view.MainView;
import com.mirhoseini.weatherapp.di.ApplicationComponent;
import com.mirhoseini.weatherapp.di.WeatherModule;
import com.mirhoseini.weatherapp.ui.fragment.CurrentWeatherFragment;
import com.mirhoseini.weatherapp.ui.fragment.ForecastWeatherFragment;
import com.mirhoseini.weatherapp.ui.fragment.HistoryWeatherFragment;

import org.openweathermap.model.WeatherHistory;
import org.openweathermap.model.WeatherMix;

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
public class MainActivity extends BaseActivity implements MainView, ForecastWeatherFragment.OnCurrentFragmentInteractionListener {

    static boolean doubleBackToExitPressedOnce;


    AlertDialog internetConnectionDialog;

    //injecting dependencies via Dagger
    @Inject
    SchedulerProvider schedulerProvider;
    @Inject
    CacheProvider cacheProvider;
    @Inject
    WeatherApiService weatherApiService;
    @Inject
    WeatherPresenter presenter;

    //injecting views via Butterknife
    @BindView(R.id.progress_container)
    ViewGroup progressContainer;
    @BindView(R.id.progress_message)
    TextView progressMessage;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.fragment_container)
    ViewGroup fragmentContainer;


    @OnEditorAction(R.id.city)
    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
        if (action == EditorInfo.IME_ACTION_GO || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            submit(textView);
        }
        return false;
    }

    @OnClick(R.id.go)
    public void submit(View view) {
        Answers.getInstance().logCustom(new CustomEvent("Pressed Go Button").putCustomAttribute("City Name", city.getText().toString()));

        //hide keyboard for better UX
        Utils.hideKeyboard(this, city);

        getWeatherData(city.getText().toString());
    }

    private void getWeatherData(String city) {
        if (city == null || city.isEmpty()) {
            //current user location can be loaded using GPS data for next version
            hideProgress();
        } else {
            //load city weather data
            presenter.loadWeather(city, Utils.isConnected(this));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding Views using ButterKnife
        ButterKnife.bind(this);

        Timber.d("Activity Created");


        //load last city from Memory using savedInstanceState or DiskCache using sharedPreferences
        String lastCity = "";

        if (savedInstanceState == null) { //load lastCity from sharePreferences
            lastCity = AppSettings.getString(this, Constants.KEY_LAST_CITY, Constants.CITY_DEFAULT_VALUE);
        } else { //load lastCity from saved state before UI change
            lastCity = savedInstanceState.getString(Constants.KEY_LAST_CITY);
        }

        city.setText(lastCity);

        getWeatherData(lastCity);
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component
                .plus(new WeatherModule(this))
                .inject(this);
    }

    @Override
    protected void onResume() {
        Timber.d("Activity Resumed");
        super.onResume();

        presenter.onResume();

        doubleBackToExitPressedOnce = false;

        // dismiss no internet connection dialog in case of getting back from setting and connection fixed
        if (internetConnectionDialog != null)
            internetConnectionDialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        Timber.d("Activity Destroyed");

        // call destroy to remove references to objects
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        Timber.d("Showing Progress");

        progressContainer.setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        Timber.d("Hiding Progress");

        progressContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setWeatherValues(WeatherMix weatherMix) {
        Timber.d("Setting Weather: %s", weatherMix.toString());

        // save last loaded data city name to disk cache for better UX
        saveLastCity(weatherMix.getWeatherCurrent().getName());

        fragmentContainer.setVisibility(View.VISIBLE);

        // load data to corresponding fragment
        CurrentWeatherFragment currentWeatherFragment = CurrentWeatherFragment.newInstance(weatherMix.getWeatherCurrent());
        ForecastWeatherFragment forecastWeatherFragment = ForecastWeatherFragment.newInstance(weatherMix.getWeatherForecast());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.current_fragment, currentWeatherFragment, "current");
        fragmentTransaction.replace(R.id.forecast_fragment, forecastWeatherFragment, "forecast");
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void setWeatherHistoryValues(WeatherHistory weatherHistory) {
        Timber.d("Setting Weather History: %s", weatherHistory.toString());

        fragmentContainer.setVisibility(View.VISIBLE);

        HistoryWeatherFragment historyWeatherFragment = HistoryWeatherFragment.newInstance(weatherHistory);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.current_fragment, historyWeatherFragment).commit();
    }

    // save user last city
    private void saveLastCity(String city) {
        Timber.d("Saving Last City");

        AppSettings.setValue(this, Constants.KEY_LAST_CITY, city);
    }

    @Override
    public void showToastMessage(String message) {
        Timber.d("Showing Toast Message: %s", message);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressMessage(String message) {
        Timber.d("Showing Progress Message: %s", message);

        progressMessage.setText(message);
    }

    @Override
    public void showOfflineMessage() {
        Timber.d("Showing Offline Message");

        Snackbar.make(city, R.string.offline_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.go_online, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(
                                Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setActionTextColor(Color.GREEN)
                .show();
    }


    @Override
    public void showExitMessage() {
        Timber.d("Showing Exit Message");

        Toast.makeText(this, R.string.msg_exit, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionError() {
        Timber.d("Showing Connection Error Message");

        if (internetConnectionDialog != null)
            internetConnectionDialog.dismiss();

        internetConnectionDialog = Utils.showNoInternetConnectionDialog(this, false);
    }

    @Override
    public void showRetryMessage() {
        Timber.d("Showing Retry Message");

        Snackbar.make(city, R.string.retry_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.load_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getWeatherData(city.getText().toString());
                    }
                })
                .setActionTextColor(Color.RED)
                .show();
    }


    @Override
    public void onBackPressed() {
        Timber.d("Activity Back Pressed");

        // check for double back press to exit
        if (doubleBackToExitPressedOnce) {
            Timber.d("Exiting");

            Utils.exit(this);
        } else {

            doubleBackToExitPressedOnce = true;

            showExitMessage();

            Timer t = new Timer();
            t.schedule(new TimerTask() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }

            }, 2500);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.d("Activity Saving Instance State");

        //save TimeSpan selected by user before data loaded and saved to SharedPreferences
        outState.putString(Constants.KEY_LAST_CITY, city.getText().toString());

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onLoadHistory(String city) {
        // load city weather history, call from forecast fragment
        presenter.loadWeatherHistory(city, Utils.isConnected(this));
    }
}