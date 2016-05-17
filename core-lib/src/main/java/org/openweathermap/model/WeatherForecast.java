package org.openweathermap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohsen on 30/04/16.
 */
public class WeatherForecast implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private Integer cod;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private List<org.openweathermap.model.Forecast> list = new ArrayList<org.openweathermap.model.Forecast>();

    /**
     * No args constructor for use in serialization
     */
    public WeatherForecast() {
    }

    /**
     * @param message
     * @param cnt
     * @param cod
     * @param list
     * @param city
     */
    public WeatherForecast(City city, Integer cod, String message, Integer cnt, List<org.openweathermap.model.Forecast> list) {
        this.city = city;
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    /**
     * @return The city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(City city) {
        this.city = city;
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
    public List<org.openweathermap.model.Forecast> getList() {
        return list;
    }

    /**
     * @param list The list
     */
    public void setList(List<org.openweathermap.model.Forecast> list) {
        this.list = list;
    }

}
