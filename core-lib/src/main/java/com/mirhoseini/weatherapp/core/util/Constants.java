package com.mirhoseini.weatherapp.core.util;

/**
 * Created by Mohsen on 30/04/16.
 */
public class Constants {
    // all Constant values are here
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "ee498803643d25e7077f98d4d9849f5c";
    public static final String HISTORY_TYPE = "hour";
    public static final String WEATHER_UNITS = "metric";
    public static final int FORECAST_DAY_COUNT = 5;
    //sharePreferences Keys
    public static final String KEY_LAST_CITY = "last_city";
    public static final String KEY_LAST_WEATHER = "last_weather";
    // default city for first run
    public static final String CITY_DEFAULT_VALUE = "Berlin";
    //cache validation time
    public static final long STALE_MS = 60 * 1000;
}
