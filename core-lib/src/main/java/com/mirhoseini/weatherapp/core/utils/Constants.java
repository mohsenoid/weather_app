package com.mirhoseini.weatherapp.core.utils;

/**
 * Created by Mohsen on 30/04/16.
 */
public class Constants {
    // all Constant values is here
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String IMAGES_URL = "http://openweathermap.org/img/w/%s.png";
    public static final String API_KEY = "ee498803643d25e7077f98d4d9849f5c";
    public static final String HISTORY_TYPE = "day";
    public static final String WEATHER_UNITS = "metric";
    public static final int FORECAST_COUNT = 5;
    public static final String LAST_CITY = "last_city";
    public static final String CITY_DEFAULT_VALUE = "Berlin";
    public static final String LAST_WEATHER = "last_weather";
    //cache validation time
    public static final long STALE_MS = 60 * 1000;
}
