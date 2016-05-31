package com.mirhoseini.weatherapp.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.mirhoseini.weatherapp.BuildConfig;
import com.mirhoseini.weatherapp.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;


/**
 * Created by Mohsen on 31/05/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityRobolectricTest {

    private MainActivity activity;
    final static String TEST_TEXT = "This is a test text.";

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(activity);
    }


    @After
    public void tearDown() throws Exception {
        activity = null;
    }

    @Test
    public void testShowProgress() {
        activity.showProgress();

        View progressContainer = activity.findViewById(R.id.progress_container);
        assertNotNull(progressContainer);
        assertThat(progressContainer.getVisibility(), equalTo(View.VISIBLE));

        View fragmentContainer = activity.findViewById(R.id.fragment_container);
        assertNotNull(fragmentContainer);
        assertThat(fragmentContainer.getVisibility(), equalTo(View.INVISIBLE));

        View errorContainer = activity.findViewById(R.id.error_container);
        assertNotNull(errorContainer);
        assertThat(errorContainer.getVisibility(), equalTo(View.INVISIBLE));
    }

    @Test
    public void testHideProgress() {
        activity.hideProgress();

        View progressContainer = activity.findViewById(R.id.progress_container);
        assertNotNull(progressContainer);
        assertThat(progressContainer.getVisibility(), equalTo(View.INVISIBLE));

        View fragmentContainer = activity.findViewById(R.id.fragment_container);
        assertNotNull(fragmentContainer);
        assertThat(fragmentContainer.getVisibility(), equalTo(View.VISIBLE));

        View errorContainer = activity.findViewById(R.id.error_container);
        assertNotNull(errorContainer);
        assertThat(errorContainer.getVisibility(), equalTo(View.INVISIBLE));
    }

    @Test
    public void testShowToastMessage() {

        activity.showToastMessage(TEST_TEXT);
        assertThat(TEST_TEXT, equalTo(ShadowToast.getTextOfLatestToast()));
    }

    @Test
    public void testShowProgressMessage() {
        activity.showProgress();
        activity.updateProgressMessage(TEST_TEXT);

        View progressContainer = activity.findViewById(R.id.progress_container);
        assertNotNull(progressContainer);
        assertThat(progressContainer.getVisibility(), equalTo(View.VISIBLE));

        View fragmentContainer = activity.findViewById(R.id.fragment_container);
        assertNotNull(fragmentContainer);
        assertThat(fragmentContainer.getVisibility(), equalTo(View.INVISIBLE));

        View errorContainer = activity.findViewById(R.id.error_container);
        assertNotNull(errorContainer);
        assertThat(errorContainer.getVisibility(), equalTo(View.INVISIBLE));

        TextView progressMessage = (TextView) activity.findViewById(R.id.progress_message);
        assertNotNull(progressMessage);
        Assert.assertThat(TEST_TEXT, equalTo(progressMessage.getText()));
    }

    @Test
    public void testShowOfflineMessage() {

    }

    @Test
    public void testShowExitMessage() {

    }

    @Test
    public void testShowConnectionError() {

    }

    @Test
    public void testShowRetryMessage() {

    }


}
