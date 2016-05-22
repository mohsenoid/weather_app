package com.mirhoseini.weatherapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mirhoseini.weatherapp.WeatherApplication;
import com.mirhoseini.weatherapp.di.ApplicationComponent;

/**
 * Created by Mohsen on 30/04/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies(WeatherApplication.getComponent());

        // can be used for general purpose in all Activities of Application

    }

    protected abstract void injectDependencies(ApplicationComponent component);

}
