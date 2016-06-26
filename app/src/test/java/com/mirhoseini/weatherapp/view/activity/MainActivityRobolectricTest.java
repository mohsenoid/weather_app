package com.mirhoseini.weatherapp.view.activity;

import android.view.View;
import android.widget.TextView;

import com.mirhoseini.weatherapp.BuildConfig;
import com.mirhoseini.weatherapp.R;
import com.mirhoseini.weatherapp.support.ShadowSnackbar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static com.mirhoseini.weatherapp.support.Assert.assertAlertDialogIsShown;
import static com.mirhoseini.weatherapp.support.Assert.assertSnackbarIsShown;
import static com.mirhoseini.weatherapp.support.Assert.assertViewIsNotVisible;
import static com.mirhoseini.weatherapp.support.Assert.assertViewIsVisible;
import static com.mirhoseini.weatherapp.support.ResourceLocator.getString;
import static com.mirhoseini.weatherapp.support.ViewLocator.getTextView;
import static com.mirhoseini.weatherapp.support.ViewLocator.getView;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by Mohsen on 31/05/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowSnackbar.class})
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
//        activity = null;
//        activity.finish();
    }

    @Test
    public void testShowProgress() throws Exception {
        activity.showProgress();

        View progressContainer = getView(activity, R.id.progress_container);
        assertViewIsVisible(progressContainer);

        View fragmentContainer = getView(activity, R.id.fragment_container);
        assertViewIsNotVisible(fragmentContainer);

        View errorContainer = getView(activity, R.id.error_container);
        assertViewIsNotVisible(errorContainer);
    }

    @Test
    public void testHideProgress() throws Exception {
        activity.hideProgress();

        View progressContainer = getView(activity, R.id.progress_container);
        assertViewIsNotVisible(progressContainer);
    }

    @Test
    public void testShowToastMessage() throws Exception {
        activity.showToastMessage(TEST_TEXT);
        assertThat(TEST_TEXT, equalTo(ShadowToast.getTextOfLatestToast()));
    }

    @Test
    public void testShowProgressMessage() throws Exception {
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
        assertThat(TEST_TEXT, equalTo(progressMessage.getText().toString()));
    }

    @Test
    public void testShowOfflineMessage() throws Exception {
        activity.showOfflineMessage();

        assertSnackbarIsShown(R.string.offline_message);
    }

    @Test
    public void testShowExitMessage() throws Exception {
        activity.showExitMessage();

        assertThat(getString(R.string.msg_exit), equalTo(ShadowToast.getTextOfLatestToast()));
    }

    @Test
    public void testShowConnectionError() throws Exception {
        activity.showConnectionError();

        assertAlertDialogIsShown(R.string.no_connection_title, R.string.no_connection);

        View errorContainer = getView(activity, R.id.error_container);
        assertViewIsVisible(errorContainer);
    }

    @Test
    public void testShowDoubleConnectionError() throws Exception {
        activity.showConnectionError();
        activity.showConnectionError();

        assertAlertDialogIsShown(R.string.no_connection_title, R.string.no_connection);

        View errorContainer = getView(activity, R.id.error_container);
        assertViewIsVisible(errorContainer);
    }


    @Test
    public void testShowRetryMessage() throws Exception {
        activity.showRetryMessage();

        assertSnackbarIsShown(R.string.retry_message);
    }

    @Test
    public void testOnDestroy() throws Exception {
//        activity.finish();
        activity.onDestroy();

        assertNull(activity.presenter);
    }

    @Test
    public void testDoubleOnBackPressed() throws Exception {
        activity.onBackPressed();

        assertThat(getString(R.string.msg_exit), equalTo(ShadowToast.getTextOfLatestToast()));

        activity.onBackPressed();

        assertTrue(activity.isFinishing());

    }

    @Test
    public void testSingleOnBackPressed() throws Exception {
        activity.onBackPressed();

        assertThat(getString(R.string.msg_exit), equalTo(ShadowToast.getTextOfLatestToast()));

        Thread.sleep(activity.DOUBLE_BACK_PRESSED_DELAY + 1);

        activity.onBackPressed();

        assertFalse(activity.isFinishing());

    }
}
