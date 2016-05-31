package com.mirhoseini.weatherapp.support;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Mohsen on 31/05/16.
 */

public class ViewLocator {
    public static View getView(Activity activity, @IdRes int id) {
        return activity.findViewById(id);
    }

    public static TextView getTextView(Activity activity, @IdRes int id) {
        return (TextView) getView(activity, id);
    }

    public static EditText getEditText(Activity activity, @IdRes int id) {
        return (EditText) getView(activity, id);
    }

    public static Button getButton(Activity activity, @IdRes int id) {
        return (Button) getView(activity, id);
    }
}
