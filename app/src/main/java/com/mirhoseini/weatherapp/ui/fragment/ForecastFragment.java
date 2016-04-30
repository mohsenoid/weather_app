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
import com.mirhoseini.weatherapp.core.service.model.WeatherForecast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastFragment extends Fragment {
    private static final String ARG_WEATHER_FORECAST = "weather_forecast";

    private WeatherForecast mWeatherForecast;

    @BindView(R.id.name)
    TextView mNameTextView;

    @BindView(R.id.description)
    TextView mDescriptionTextView;

    @BindView(R.id.icon)
    AppCompatImageView mIconImageView;

//    private OnFragmentInteractionListener mListener;

    public ForecastFragment() {
        // Required empty public constructor
    }

    public static ForecastFragment newInstance(WeatherForecast weatherForecast) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_FORECAST, weatherForecast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWeatherForecast = (WeatherForecast) getArguments().getSerializable(ARG_WEATHER_FORECAST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        ButterKnife.bind(this, view);

        mNameTextView.setText(mWeatherForecast.getCity().getName());
//        mDescriptionTextView.setText(mWeatherForecast.getMessage());
//        mIconImageView.setImageResource(convertIconToResource(weather.getIcon()));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
//        mListener = null;
    }

//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }
}
