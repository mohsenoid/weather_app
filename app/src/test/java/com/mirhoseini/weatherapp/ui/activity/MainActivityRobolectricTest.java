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

import static com.mirhoseini.weatherapp.support.Assert.assertViewIsNotVisible;
import static com.mirhoseini.weatherapp.support.Assert.assertViewIsVisible;
import static com.mirhoseini.weatherapp.support.ViewLocator.getTextView;
import static com.mirhoseini.weatherapp.support.ViewLocator.getView;
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

        View progressContainer = getView(activity, R.id.progress_container);
        assertViewIsVisible(progressContainer);

        View fragmentContainer = getView(activity, R.id.fragment_container);
        assertViewIsNotVisible(fragmentContainer);

        View errorContainer = getView(activity, R.id.error_container);
        assertViewIsNotVisible(errorContainer);
    }

    @Test
    public void testHideProgress() {
        activity.hideProgress();

        View progressContainer = getView(activity, R.id.progress_container);
        assertViewIsNotVisible(progressContainer);
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

        View progressContainer = getView(activity, R.id.progress_container);
        assertViewIsVisible(progressContainer);

        View fragmentContainer = getView(activity, R.id.fragment_container);
        assertViewIsNotVisible(fragmentContainer);

        View errorContainer = getView(activity, R.id.error_container);
        assertViewIsNotVisible(errorContainer);

        TextView progressMessage = getTextView(activity, R.id.progress_message);
        assertViewIsVisible(progressMessage);
        Assert.assertThat(TEST_TEXT, equalTo(progressMessage.getText().toString()));
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
