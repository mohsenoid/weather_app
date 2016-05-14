package org.openweathermap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mohsen on 30/04/16.
 */
public class Wind implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Double deg;

    /**
     * No args constructor for use in serialization
     */
    public Wind() {
    }

    /**
     * @param speed
     * @param deg
     */
    public Wind(Double speed, Double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    /**
     * @return The speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * @param speed The speed
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     * @return The deg
     */
    public Double getDeg() {
        return deg;
    }

    /**
     * @param deg The deg
     */
    public void setDeg(Double deg) {
        this.deg = deg;
    }


//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof Wind) == false) {
//            return false;
//        }
//        Wind rhs = ((Wind) other);
//        return speed == rhs.speed && deg == rhs.deg;
//    }

}
