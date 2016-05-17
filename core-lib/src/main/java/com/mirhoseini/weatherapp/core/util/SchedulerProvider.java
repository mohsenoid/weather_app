package com.mirhoseini.weatherapp.core.util;

import rx.Scheduler;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
