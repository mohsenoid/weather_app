package com.mirhoseini.weatherapp;

import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.model.WeatherInteractorImpl;
import com.mirhoseini.weatherapp.core.service.WeatherApiServiceImpl;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.utils.AppCacheProvider;
import com.mirhoseini.weatherapp.utils.AppSchedulerProvider;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openweathermap.model.WeatherCurrent;
import org.openweathermap.model.WeatherForecast;
import org.openweathermap.model.WeatherMix;
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
    private WeatherInteractorImpl weatherInteractorImpl;
    private WeatherApiServiceImpl weatherApiNetworkServiceImpl;
    private AppCacheProvider appCacheProvider;
    private AppSchedulerProvider appScheduler;
    private Clock clock;
    private WeatherMix expectedResult;
    private WeatherMix nonExpectedResult;
    private WeatherMix newExpectedResult;

    @Before
    public void setup() {
        weatherApiNetworkServiceImpl = mock(WeatherApiServiceImpl.class);
        appCacheProvider = mock(AppCacheProvider.class);
        appScheduler = mock(AppSchedulerProvider.class);
        clock = mock(Clock.class);

        expectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        expectedResult.getWeatherCurrent().setName("Berlin");
        expectedResult.getWeatherCurrent().setDt(0L);

        nonExpectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        nonExpectedResult.getWeatherCurrent().setName("Tehran");
        nonExpectedResult.getWeatherCurrent().setDt(0L);

        newExpectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        newExpectedResult.getWeatherCurrent().setName("Berlin");
        newExpectedResult.getWeatherCurrent().setDt(Constants.STALE_MS + 1);

        when(appCacheProvider.saveWeather(any())).thenReturn(Observable.just(null));

        when(appScheduler.mainThread()).thenReturn(Schedulers.immediate());
        when(appScheduler.backgroundThread()).thenReturn(Schedulers.immediate());

        weatherInteractorImpl = new WeatherInteractorImpl(appCacheProvider, weatherApiNetworkServiceImpl, appScheduler, clock);
    }

    @Test
    public void stage1_testHitsMemoryCache() {
        when(weatherApiNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(expectedResult));
        when(appCacheProvider.getWeather()).thenReturn(Observable.just(null));
        when(clock.millis()).thenReturn(0L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(expectedResult));

        when(appCacheProvider.getWeather()).thenReturn(Observable.just(nonExpectedResult));
        when(weatherApiNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(nonExpectedResult));

        // must load data from Memory before checking cache or Network, cause the answer is there
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(expectedResult));
    }

    @Test
    public void stage2_testHitsDiskCache() {
        when(weatherApiNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(expectedResult));
        when(appCacheProvider.getWeather()).thenReturn(Observable.just(null));
        when(clock.millis()).thenReturn(0L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(expectedResult));

        weatherInteractorImpl.clearMemoryCache();
        when(appCacheProvider.getWeather()).thenReturn(Observable.just(expectedResult));
        when(weatherApiNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(nonExpectedResult));

        // must load data from Cache after checking Memory which is null
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(expectedResult));
    }

    @Test
    public void stage3_testCacheExpiry() {
        when(weatherApiNetworkServiceImpl.loadWeather(any(String.class))).thenReturn(Observable.just(newExpectedResult));
        when(appCacheProvider.getWeather()).thenReturn(Observable.just(expectedResult));
        when(clock.millis()).thenReturn(0L);

        // load weather from Cache but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(expectedResult));

        when(clock.millis()).thenReturn(Constants.STALE_MS - 1);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(expectedResult));

        when(clock.millis()).thenReturn(Constants.STALE_MS);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberThird = new TestSubscriber<>();
        weatherInteractorImpl.loadWeather("Berlin").subscribe(testSubscriberThird);
        testSubscriberThird.assertNoErrors();
        testSubscriberThird.assertReceivedOnNext(singletonList(newExpectedResult));
    }
}