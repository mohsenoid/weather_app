package com.mirhoseini.weatherapp.network;

/**
 * Created by Mohsen on 30/04/16.
 */
public interface OnNetworkFinishedListener<T> {

    void onSuccess(T restResponse);

    void onError(Throwable throwable);
}
