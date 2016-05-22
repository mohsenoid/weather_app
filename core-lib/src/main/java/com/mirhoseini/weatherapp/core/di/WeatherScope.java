package com.mirhoseini.weatherapp.core.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Mohsen on 22/05/16.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface WeatherScope {
}
