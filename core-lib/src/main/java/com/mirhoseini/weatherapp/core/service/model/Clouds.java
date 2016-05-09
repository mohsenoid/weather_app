package com.mirhoseini.weatherapp.core.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mohsen on 30/04/16.
 */
public class Clouds implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @SerializedName("all")
    @Expose
    private Integer all;

    /**
     * No args constructor for use in serialization
     */
    public Clouds() {
    }

    /**
     * @param all
     */
    public Clouds(Integer all) {
        this.all = all;
    }

    /**
     * @return The all
     */
    public Integer getAll() {
        return all;
    }

    /**
     * @param all The all
     */
    public void setAll(Integer all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "";
    }

//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof Clouds) == false) {
//            return false;
//        }
//        Clouds rhs = ((Clouds) other);
//        return all == rhs.all;
//    }
//
//    @Override
//    public int hashCode() {
//        return all.hashCode();
//    }

}
