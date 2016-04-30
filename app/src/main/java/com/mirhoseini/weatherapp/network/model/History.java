
package com.mirhoseini.weatherapp.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class History {

    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("hr_time")
    @Expose
    private Integer hrTime;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("dt")
    @Expose
    private Integer dt;

    /**
     * No args constructor for use in serialization
     */
    public History() {
    }

    /**
     * @param dt
     * @param clouds
     * @param wind
     * @param time
     * @param hrTime
     * @param weather
     * @param main
     */
    public History(Integer time, Integer hrTime, Main main, Wind wind, Clouds clouds, java.util.List<Weather> weather, Integer dt) {
        this.time = time;
        this.hrTime = hrTime;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.weather = weather;
        this.dt = dt;
    }

    /**
     * @return The time
     */
    public Integer getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * @return The hrTime
     */
    public Integer getHrTime() {
        return hrTime;
    }

    /**
     * @param hrTime The hr_time
     */
    public void setHrTime(Integer hrTime) {
        this.hrTime = hrTime;
    }

    /**
     * @return The main
     */
    public Main getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * @return The wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * @param wind The wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * @return The clouds
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     * @param clouds The clouds
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * @return The weather
     */
    public java.util.List<Weather> getWeather() {
        return weather;
    }

    /**
     * @param weather The weather
     */
    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    /**
     * @return The dt
     */
    public Integer getDt() {
        return dt;
    }

    /**
     * @param dt The dt
     */
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof History) == false) {
            return false;
        }
        History rhs = ((History) other);
        return time == rhs.time && hrTime == rhs.hrTime && main.equals(rhs.main);
    }

}
