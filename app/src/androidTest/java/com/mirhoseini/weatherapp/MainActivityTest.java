package com.mirhoseini.weatherapp;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mirhoseini.utils.Utils;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.ui.activity.MainActivity;

import org.junit.Test;


/**
 * Created by Mohsen on 24/03/16.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;
    private EditText mCityEditText;
    private Button mGoButton;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mCityEditText = (EditText) mActivity.findViewById(R.id.city_edittext);
        mGoButton = (Button) mActivity.findViewById(R.id.go_button);
    }

    @Test
    public void testPreConditions() {
        assertNotNull("city EditText not displayed",mCityEditText);
        assertNotNull("go Button not displayed",mGoButton);
    }

    @Test
    public void testWeatherValue() throws InterruptedException {
        assertTrue("no internet connection", Utils.isConnected(mActivity));

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCityEditText.setText(Constants.CITY_DEFAULT_VALUE);
                mGoButton.performClick();
            }
        });

        // wait some time till data loaded
        Thread.sleep(5 * 1000);
        TextView cityTextView = (TextView) mActivity.findViewById(R.id.name);

        assertNotNull("no weather data loaded", cityTextView);
        assertEquals("wrong city data loaded", Constants.CITY_DEFAULT_VALUE, cityTextView.getText().toString());
    }

}