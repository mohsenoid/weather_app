package com.mirhoseini.weatherapp.support;

import android.support.annotation.StringRes;

import org.robolectric.RuntimeEnvironment;

/**
 * Created by Mohsen on 31/05/16.
 */

public class ResourceLocator {
    public static String getString(@StringRes int id) {
        return RuntimeEnvironment.application.getString(id);
    }
}
