package com.mirhoseini.weatherapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirhoseini.weatherapp.R;
import com.mirhoseini.weatherapp.core.service.model.WeatherHistory;

public class HistoryFragment extends Fragment {
    private static final String ARG_WEATHER_HISTORY = "weather_history";

    private WeatherHistory mWeatherHistory;

//    private OnCurrentFragmentInteractionListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(WeatherHistory weatherHistory) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_HISTORY, weatherHistory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWeatherHistory = (WeatherHistory) getArguments().getSerializable(ARG_WEATHER_HISTORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnCurrentFragmentInteractionListener) {
//            mListener = (OnCurrentFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnCurrentFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

//    public interface OnCurrentFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }
}
