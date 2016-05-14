package com.mirhoseini.weatherapp.core.utils;

import rx.Scheduler;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
