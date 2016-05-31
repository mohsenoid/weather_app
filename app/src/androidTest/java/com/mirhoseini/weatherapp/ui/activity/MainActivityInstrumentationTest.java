package com.mirhoseini.weatherapp.ui.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mirhoseini.utils.Utils;
import com.mirhoseini.weatherapp.R;
import com.mirhoseini.weatherapp.core.util.Constants;
import com.mirhoseini.weatherapp.ui.activity.MainActivity;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


/**
 * Created by Mohsen on 30/04/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainActivityInstrumentationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;
    private EditText mCityEditText;
    private Button mGoButton;

    public MainActivityInstrumentationTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);
        mActivity = getActivity();
        mCityEditText = (EditText) mActivity.findViewById(R.id.city);
        mGoButton = (Button) mActivity.findViewById(R.id.go);
    }

    @Test
    public void stage1_testPreConditions() {
        assertNotNull("city EditText not displayed", mCityEditText);
        assertNotNull("go Button not displayed", mGoButton);
    }

    @Test
    public void stage2_testWeatherValue() throws InterruptedException {
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