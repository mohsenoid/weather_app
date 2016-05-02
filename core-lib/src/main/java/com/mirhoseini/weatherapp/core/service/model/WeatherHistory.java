package com.mirhoseini.weatherapp.core.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherHistory implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("calctime")
    @Expose
    private Double calctime;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private List<History> list = new ArrayList<History>();

    /**
     * No args constructor for use in serialization
     */
    public WeatherHistory() {
    }

    /**
     * @param message
     * @param cnt
     * @param cod
     * @param cityId
     * @param calctime
     * @param list
     */
    public WeatherHistory(String message, String cod, Integer cityId, Double calctime, Integer cnt, List<History> list) {
        this.message = message;
        this.cod = cod;
        this.cityId = cityId;
        this.calctime = calctime;
        this.cnt = cnt;
        this.list = list;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     * @param cod The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     * @return The cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * @param cityId The city_id
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * @return The calctime
     */
    public Double getCalctime() {
        return calctime;
    }

    /**
     * @param calctime The calctime
     */
    public void setCalctime(Double calctime) {
        this.calctime = calctime;
    }

    /**
     * @return The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     * @param cnt The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     * @return The list
     */
    public List<History> getList() {
        return list;
    }

    /**
     * @param list The list
     */
    public void setList(List<History> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof WeatherHistory) == false) {
            return false;
        }
        WeatherHistory rhs = ((WeatherHistory) other);
        return cod == rhs.cod && cityId == rhs.cityId;
    }

}
