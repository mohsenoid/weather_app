package com.mirhoseini.weatherapp.core.utils;

import rx.Scheduler;

/**
 * Created by Mohsen on 4/30/16.
 */
public interface IScheduler {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
