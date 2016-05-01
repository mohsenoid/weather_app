package com.mirhoseini.weatherapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirhoseini.weatherapp.R;
import com.mirhoseini.weatherapp.core.service.model.Weather;
import com.mirhoseini.weatherapp.core.service.model.WeatherCurrent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrentFragment extends Fragment {
    private static final String ARG_WEATHER_CURRENT = "weather_current";

    private WeatherCurrent mWeatherCurrent;

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
    private String mCity;

    @OnClick(R.id.history_button)
    public void history(View view) {
        if (mListener != null) {
            mListener.onLoadHistory(mCity);
        }
    }

    private OnCurrentFragmentInteractionListener mListener;

    public CurrentFragment() {
        // Required empty public constructor
    }

    public static CurrentFragment newInstance(WeatherCurrent weatherCurrent) {
        CurrentFragment fragment = new CurrentFragment();
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

        Weather weather = mWeatherCurrent.getWeather().get(0);
        mCity = mWeatherCurrent.getName();

        mNameTextView.setText(mCity);

        mDescriptionTextView.setText(weather.getDescription());

        mTempTextView.setText(mWeatherCurrent.getMain().getTemp() + "°C");

        mIconImageView.setImageResource(convertIconToResource(weather.getIcon()));

        mWindSpeedTextView.setText(mWeatherCurrent.getWind().getSpeed() + "m/s");

        mTempHighTextView.setText(mWeatherCurrent.getMain().getTempMax() + "°C");

        mTempLowTextView.setText(mWeatherCurrent.getMain().getTempMin() + "°C");


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof OnCurrentFragmentInteractionListener) {
            mListener = (OnCurrentFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCurrentFragmentInteractionListener");
        }
    }

    private int convertIconToResource(String icon) {
        switch (icon) {
            case "01d":
                return R.drawable.ic_sun;
            case "01n":
                return R.drawable.ic_moon;
            case "02d":
                return R.drawable.ic_sun_cloudy;
            case "02n":
                return R.drawable.ic_moon_cloud;
            case "03d":
            case "03n":
            case "04d":
            case "04n":
                return R.drawable.ic_cloud;
            case "09d":
            case "09n":
            case "10d":
            case "10n":
                return R.drawable.ic_rain;
            case "11d":
            case "11n":
                return R.drawable.ic_storm;
            case "13d":
            case "13n":
                return R.drawable.ic_snow;
            default:
                return 0;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCurrentFragmentInteractionListener {
        void onLoadHistory(String city);
    }
}
