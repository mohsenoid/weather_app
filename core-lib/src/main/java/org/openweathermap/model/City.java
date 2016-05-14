package org.openweathermap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Mohsen on 30/04/16.
 */
public class City implements Serializable {
    //TODO: replace Serializable with Parcelable for better performance

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("sys")
    @Expose
    private Sys sys;

    /**
     * No args constructor for use in serialization
     */
    public City() {
    }

    /**
     * @param coord
     * @param id
     * @param sys
     * @param name
     * @param population
     * @param country
     */
    public City(Integer id, String name, Coord coord, String country, Integer population, Sys sys) {
        this.id = id;
        this.name = name;
        this.coord = coord;
        this.country = country;
        this.population = population;
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
     * @return The population
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     * @param population The population
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     * @return The sys
     */
    public Sys getSys() {
        return sys;
    }

    /**
     * @param sys The sys
     */
    public void setSys(Sys sys) {
        this.sys = sys;
    }

//    @Override
//    public boolean equals(Object other) {
//        if (other == this) {
//            return true;
//        }
//        if ((other instanceof City) == false) {
//            return false;
//        }
//        City rhs = ((City) other);
//        return id == rhs.id;
//    }
//
//    public int hashCode() {
//        // you pick a hard-coded, randomly chosen, non-zero, odd number
//        // ideally different for each class
//        return Objects.hashCode(id,name,coord,country,population,sys,name);
//    }
}
