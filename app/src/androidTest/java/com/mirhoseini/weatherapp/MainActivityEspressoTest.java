package com.mirhoseini.weatherapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mirhoseini.weatherapp.ui.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
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
        onView(withId(R.id.city_edittext)).check(matches(isDisplayed()));
        onView(withId(R.id.go_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testUiFunctionality() {
        testWeatherResultForCity("Tehran");
        testWeatherResultForCity("Berlin");
    }

    private void testWeatherResultForCity(String cityName) {
        onView(withId(R.id.city_edittext)).perform(clearText());
        onView(withId(R.id.city_edittext)).perform(typeText(cityName));
        onView(withId(R.id.go_button)).perform(click());
        onView(withId(R.id.name)).check(matches(withText(cityName)));
    }
}
