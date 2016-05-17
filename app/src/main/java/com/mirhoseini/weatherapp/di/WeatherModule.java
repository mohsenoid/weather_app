package com.mirhoseini.weatherapp.di;

import com.mirhoseini.weatherapp.core.model.WeatherInteractor;
import com.mirhoseini.weatherapp.core.model.WeatherInteractorImpl;
import com.mirhoseini.weatherapp.core.presentation.WeatherPresenter;
import com.mirhoseini.weatherapp.core.presentation.WeatherPresenterImpl;
import com.mirhoseini.weatherapp.core.view.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 5/17/16.
 */
@Module
public class WeatherModule {
    private MainView view;

    public WeatherModule(MainView view) {
        this.view = view;
    }

    @Provides
    public MainView provideView() {
        return view;
    }

    @Provides
    public WeatherPresenter providePresenter(WeatherPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    public WeatherInteractor provideInteractor(WeatherInteractorImpl interactor) {
        return interactor;
    }
}
