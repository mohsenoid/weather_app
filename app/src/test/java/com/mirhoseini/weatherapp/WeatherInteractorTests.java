package com.mirhoseini.weatherapp;

import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.model.WeatherInteractorImpl;
import com.mirhoseini.weatherapp.core.service.WeatherServiceImpl;
import org.openweathermap.model.WeatherCurrent;
import org.openweathermap.model.WeatherForecast;
import org.openweathermap.model.WeatherMix;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.utils.AppCacheProvider;
import com.mirhoseini.weatherapp.utils.AppSchedulerProvider;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mohsen on 30/04/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Config(constants = BuildConfig.class,
        application = WeatherApplication.class,
        sdk = 21)
public class WeatherInteractorTests {
    private WeatherInteractorImpl mWeatherInteractorImpl;
    private WeatherServiceImpl mWeatherNetworkServiceImpl;
    private AppCacheProvider mAppCacheProvider;
    private AppSchedulerProvider mAppScheduler;
    private Clock mClock;
    private WeatherMix mExpectedResult;
    private WeatherMix mNonExpectedResult;
    private WeatherMix mNewExpectedResult;

    @Before
    public void setup() {
        mWeatherNetworkServiceImpl = mock(WeatherServiceImpl.class);
        mAppCacheProvider = mock(AppCacheProvider.class);
        mAppScheduler = mock(AppSchedulerProvider.class);
        mClock = mock(Clock.class);

        mExpectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        mExpectedResult.getWeatherCurrent().setName("Berlin");
        mExpectedResult.getWeatherCurrent().setDt(0L);

        mNonExpectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        mNonExpectedResult.getWeatherCurrent().setName("Tehran");
        mNonExpectedResult.getWeatherCurrent().setDt(0L);

        mNewExpectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        mNewExpectedResult.getWeatherCurrent().setName("Berlin");
        mNewExpectedResult.getWeatherCurrent().setDt(Constants.STALE_MS + 1);

        when(mAppCacheProvider.saveWeather(any())).thenReturn(Observable.just(null));

        when(mAppScheduler.mainThread()).thenReturn(Schedulers.immediate());
        when(mAppScheduler.backgroundThread()).thenReturn(Schedulers.immediate());

        mWeatherInteractorImpl = new WeatherInteractorImpl(mAppCacheProvider, mWeatherNetworkServiceImpl, mAppScheduler, mClock);
    }

    @Test
    public void stage1_testHitsMemoryCache() {
        when(mWeatherNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(mExpectedResult));
        when(mAppCacheProvider.getWeather()).thenReturn(Observable.just(null));
        when(mClock.millis()).thenReturn(0L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        mWeatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(mExpectedResult));

        when(mAppCacheProvider.getWeather()).thenReturn(Observable.just(mNonExpectedResult));
        when(mWeatherNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(mNonExpectedResult));

        // must load data from Memory before checking cache or Network, cause the answer is there
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        mWeatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(mExpectedResult));
    }

    @Test
    public void stage2_testHitsDiskCache() {
        when(mWeatherNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(mExpectedResult));
        when(mAppCacheProvider.getWeather()).thenReturn(Observable.just(null));
        when(mClock.millis()).thenReturn(0L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        mWeatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(mExpectedResult));

        mWeatherInteractorImpl.clearMemoryCache();
        when(mAppCacheProvider.getWeather()).thenReturn(Observable.just(mExpectedResult));
        when(mWeatherNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(mNonExpectedResult));

        // must load data from Cache after checking Memory which is null
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        mWeatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(mExpectedResult));
    }

    @Test
    public void stage3_testCacheExpiry() {
        when(mWeatherNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(mNewExpectedResult));
        when(mAppCacheProvider.getWeather()).thenReturn(Observable.just(mExpectedResult));
        when(mClock.millis()).thenReturn(0L);

        // load weather from Cache but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        mWeatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(mExpectedResult));

        when(mClock.millis()).thenReturn(Constants.STALE_MS - 1);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        mWeatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(mExpectedResult));

        when(mClock.millis()).thenReturn(Constants.STALE_MS);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberThird = new TestSubscriber<>();
        mWeatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberThird);
        testSubscriberThird.assertNoErrors();
        testSubscriberThird.assertReceivedOnNext(singletonList(mNewExpectedResult));
    }
}