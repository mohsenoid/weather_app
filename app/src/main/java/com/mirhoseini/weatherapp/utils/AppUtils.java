package com.mirhoseini.weatherapp.utils;

import com.mirhoseini.weatherapp.R;

/**
 * Created by Mohsen on 5/1/16.
 */
public class AppUtils {
    public static int convertIconToResource(String icon) {
        switch (icon) {
            case "01d":
                return R.drawable.ic_sun;
            case "01n":
                return R.drawable.ic_moon;
            case "02d":
                return R.drawable.ic_sun_cloudy;
            case "02n":
                return R.drawable.ic_moon_cloud;
            case "03d":
            case "03n":
            case "04d":
            case "04n":
                return R.drawable.ic_cloud;
            case "09d":
            case "09n":
            case "10d":
            case "10n":
                return R.drawable.ic_rain;
            case "11d":
            case "11n":
                return R.drawable.ic_storm;
            case "13d":
            case "13n":
                return R.drawable.ic_snow;
            default:
                return 0;
        }
    }
}
