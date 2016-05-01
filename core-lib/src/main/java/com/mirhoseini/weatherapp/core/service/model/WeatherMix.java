package com.mirhoseini.weatherapp.core.service.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Mohsen on 4/30/16.
 */
public class WeatherMix implements Serializable {
    //TODO: replace Serializable with parcelable

    @Expose
    private WeatherCurrent mWeatherCurrent;

    @Expose
    private WeatherForecast mWeatherForecast;

    @Expose
    private long dt;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public WeatherMix(WeatherCurrent weatherCurrent, WeatherForecast weatherForecast) {
        mWeatherCurrent = weatherCurrent;
        mWeatherForecast = weatherForecast;
    }

    public WeatherCurrent getWeatherCurrent() {
        return mWeatherCurrent;
    }

    public void setWeatherCurrent(WeatherCurrent weatherCurrent) {
        mWeatherCurrent = weatherCurrent;
    }

    public WeatherForecast getWeatherForecast() {
        return mWeatherForecast;
    }

    public void setWeatherForecast(WeatherForecast weatherForecast) {
        mWeatherForecast = weatherForecast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherMix that = (WeatherMix) o;

        if (mWeatherCurrent != null ? !mWeatherCurrent.equals(that.mWeatherCurrent) : that.mWeatherCurrent != null)
            return false;
        return mWeatherForecast != null ? mWeatherForecast.equals(that.mWeatherForecast) : that.mWeatherForecast == null;

    }

}
