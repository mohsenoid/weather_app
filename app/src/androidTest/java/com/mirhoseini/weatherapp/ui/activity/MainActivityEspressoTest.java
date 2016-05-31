package com.mirhoseini.weatherapp.ui.activity;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mirhoseini.weatherapp.R;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Mohsen on 5/10/16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testUiDisplayedCorrectly() {
        onView(ViewMatchers.withId(R.id.city)).check(matches(isDisplayed()));
        onView(withId(R.id.go)).check(matches(isDisplayed()));
    }

    @Test
    public void testUiFunctionality() {
        testWeatherResultForCity("Tehran");
        testWeatherResultForCity("Berlin");
    }

    private void testWeatherResultForCity(String cityName) {
        onView(withId(R.id.city)).perform(replaceText(cityName));
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.name)).check(matches(withText(cityName)));
    }
}
