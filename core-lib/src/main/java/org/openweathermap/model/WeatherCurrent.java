package org.openweathermap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherCurrent implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private List<org.openweathermap.model.Weather> weather = new ArrayList<org.openweathermap.model.Weather>();
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private org.openweathermap.model.Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private org.openweathermap.model.Clouds clouds;
    @SerializedName("dt")
    @Expose
    private long dt;
    @SerializedName("sys")
    @Expose
    private org.openweathermap.model.Sys sys;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    /**
     * No args constructor for use in serialization
     */
    public WeatherCurrent() {
    }

    /**
     * @param id
     * @param dt
     * @param clouds
     * @param coord
     * @param wind
     * @param cod
     * @param sys
     * @param name
     * @param base
     * @param weather
     * @param main
     */
    public WeatherCurrent(Coord coord, List<org.openweathermap.model.Weather> weather, String base, org.openweathermap.model.Main main, Wind wind, org.openweathermap.model.Clouds clouds, long dt, org.openweathermap.model.Sys sys, Integer id, String name, Integer cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    /**
     * @return The coord
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * @param coord The coord
     */
    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    /**
     * @return The weather
     */
    public List<org.openweathermap.model.Weather> getWeather() {
        return weather;
    }

    /**
     * @param weather The weather
     */
    public void setWeather(List<org.openweathermap.model.Weather> weather) {
        this.weather = weather;
    }

    /**
     * @return The base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @return The main
     */
    public org.openweathermap.model.Main getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(org.openweathermap.model.Main main) {
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
    public org.openweathermap.model.Clouds getClouds() {
        return clouds;
    }

    /**
     * @param clouds The clouds
     */
    public void setClouds(org.openweathermap.model.Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * @return The dt
     */
    public long getDt() {
        return dt;
    }

    /**
     * @param dt The dt
     */
    public void setDt(long dt) {
        this.dt = dt;
    }

    /**
     * @return The sys
     */
    public org.openweathermap.model.Sys getSys() {
        return sys;
    }

    /**
     * @param sys The sys
     */
    public void setSys(org.openweathermap.model.Sys sys) {
        this.sys = sys;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The cod
     */
    public Integer getCod() {
        return cod;
    }

    /**
     * @param cod The cod
     */
    public void setCod(Integer cod) {
        this.cod = cod;
    }

//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof WeatherCurrent) == false) {
//            return false;
//        }
//        WeatherCurrent rhs = ((WeatherCurrent) other);
//        return coord.equals(rhs.coord);
//    }

}
