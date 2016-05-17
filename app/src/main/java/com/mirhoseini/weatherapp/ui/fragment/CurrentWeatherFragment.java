package com.mirhoseini.weatherapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirhoseini.weatherapp.R;
import com.mirhoseini.weatherapp.util.WeatherUtils;

import org.openweathermap.model.Weather;
import org.openweathermap.model.WeatherCurrent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohsen on 30/04/16.
 */
public class CurrentWeatherFragment extends Fragment {
    private static final String ARG_WEATHER_CURRENT = "weather_current";
    @BindView(R.id.name)
    TextView mNameTextView;
    @BindView(R.id.description)
    TextView mDescriptionTextView;
    @BindView(R.id.icon)
    AppCompatImageView mIconImageView;
    @BindView(R.id.temp)
    TextView mTempTextView;
    @BindView(R.id.windspeed)
    TextView mWindSpeedTextView;
    @BindView(R.id.temp_high)
    TextView mTempHighTextView;
    @BindView(R.id.temp_low)
    TextView mTempLowTextView;
    private WeatherCurrent mWeatherCurrent;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(WeatherCurrent weatherCurrent) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_CURRENT, weatherCurrent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWeatherCurrent = (WeatherCurrent) getArguments().getSerializable(ARG_WEATHER_CURRENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        ButterKnife.bind(this, view);

        // load weather data to view
        if (mWeatherCurrent != null) {

            Weather weather = mWeatherCurrent.getWeather().get(0);

            mNameTextView.setText(mWeatherCurrent.getName());

            mDescriptionTextView.setText(weather.getDescription());

            mTempTextView.setText(mWeatherCurrent.getMain().getTemp() + "°C");

            mIconImageView.setImageResource(WeatherUtils.convertIconToResource(weather.getIcon()));

            mWindSpeedTextView.setText(mWeatherCurrent.getWind().getSpeed() + "m/s");

            mTempHighTextView.setText(mWeatherCurrent.getMain().getTempMax() + "°C");

            mTempLowTextView.setText(mWeatherCurrent.getMain().getTempMin() + "°C");
        }

        return view;
    }

}
