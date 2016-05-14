package org.openweathermap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mohsen on 30/04/16.
 */
public class Sys implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Integer sunrise;
    @SerializedName("sunset")
    @Expose
    private Integer sunset;
    @SerializedName("pod")
    @Expose
    private String pod;

    /**
     * No args constructor for use in serialization
     */
    public Sys() {
    }

    /**
     * @param message
     * @param sunset
     * @param sunrise
     * @param country
     */
    public Sys(Double message, String country, Integer sunrise, Integer sunset, String pod) {
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.pod = pod;
    }

    /**
     * @return The message
     */
    public Double getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(Double message) {
        this.message = message;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The sunrise
     */
    public Integer getSunrise() {
        return sunrise;
    }

    /**
     * @param sunrise The sunrise
     */
    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * @return The sunset
     */
    public Integer getSunset() {
        return sunset;
    }

    /**
     * @param sunset The sunset
     */
    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    /**
     * @return The pod
     */
    public String getPod() {
        return pod;
    }

    /**
     * @param pod The pod
     */
    public void setPod(String pod) {
        this.pod = pod;
    }

//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof Sys) == false) {
//            return false;
//        }
//        Sys rhs = ((Sys) other);
//        return message == rhs.message && country.equalsIgnoreCase(rhs.country) && sunrise == rhs.sunrise && sunset == rhs.sunset && pod.equals(rhs.pod);
//    }

}
