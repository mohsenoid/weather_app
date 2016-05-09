package com.mirhoseini.weatherapp.core.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mohsen on 30/04/16.
 */
public class Coord implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lat")
    @Expose
    private Double lat;

    /**
     * No args constructor for use in serialization
     */
    public Coord() {
    }

    /**
     * @param lon
     * @param lat
     */
    public Coord(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    /**
     * @return The longitude
     */
    public Double getLon() {
        return lon;
    }

    /**
     * @param lon The longitude
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    /**
     * @return The latitude
     */
    public Double getLat() {
        return lat;
    }

    /**
     * @param lat The latitude
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof Coord) == false) {
//            return false;
//        }
//        Coord rhs = ((Coord) other);
//        return lon == rhs.lon && lat == rhs.lat;
//    }
//
//    @Override
//    public int hashCode() {
//        return lat.hashCode() + lon.hashCode();
//    }

}
