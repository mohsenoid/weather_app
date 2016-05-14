package org.openweathermap.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherMix implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @Expose
    private WeatherCurrent mWeatherCurrent;

    @Expose
    private org.openweathermap.model.WeatherForecast mWeatherForecast;

    @Expose
    private long dt;

    public WeatherMix(WeatherCurrent weatherCurrent, org.openweathermap.model.WeatherForecast weatherForecast) {
        mWeatherCurrent = weatherCurrent;
        mWeatherForecast = weatherForecast;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public WeatherCurrent getWeatherCurrent() {
        return mWeatherCurrent;
    }

    public void setWeatherCurrent(WeatherCurrent weatherCurrent) {
        mWeatherCurrent = weatherCurrent;
    }

    public org.openweathermap.model.WeatherForecast getWeatherForecast() {
        return mWeatherForecast;
    }

    public void setWeatherForecast(org.openweathermap.model.WeatherForecast weatherForecast) {
        mWeatherForecast = weatherForecast;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        WeatherMix that = (WeatherMix) o;
//
//        if (mWeatherCurrent != null ? !mWeatherCurrent.equals(that.mWeatherCurrent) : that.mWeatherCurrent != null)
//            return false;
//        return mWeatherForecast != null ? mWeatherForecast.equals(that.mWeatherForecast) : that.mWeatherForecast == null;
//
//    }

}
