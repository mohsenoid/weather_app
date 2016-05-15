package com.mirhoseini.weatherapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mirhoseini.weatherapp.ui.activity.MainActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void stage1_testUiDisplayedCorrectly() {
        onView(withId(R.id.city)).check(matches(isDisplayed()));
        onView(withId(R.id.go)).check(matches(isDisplayed()));
    }

    @Test
    public void stage2_testUiFunctionality() {
        testWeatherResultForCity("Tehran");
        testWeatherResultForCity("Berlin");
    }

    private void testWeatherResultForCity(String cityName) {
        onView(withId(R.id.city)).perform(clearText());
        onView(withId(R.id.city)).perform(typeText(cityName));
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.name)).check(matches(withText(cityName)));
    }
}
