package com.mirhoseini.weatherapp.core.model;

import com.mirhoseini.weatherapp.core.service.WeatherApiService;
import com.mirhoseini.weatherapp.core.util.CacheProvider;
import com.mirhoseini.weatherapp.core.util.Constants;
import com.mirhoseini.weatherapp.core.util.SchedulerProvider;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openweathermap.model.WeatherCurrent;
import org.openweathermap.model.WeatherForecast;
import org.openweathermap.model.WeatherMix;

import java.util.Collections;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mohsen on 30/04/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WeatherInteractorTests {
    private WeatherInteractor weatherInteractor;
    private WeatherApiService weatherApiService;
    private CacheProvider cacheProvider;
    private SchedulerProvider schedulerProvider;
    private Clock clock;
    private WeatherMix expectedResult;
    private WeatherMix nonExpectedResult;
    private WeatherMix newExpectedResult;

    @Before
    public void setup() {
        weatherApiService = mock(WeatherApiService.class);
        cacheProvider = mock(CacheProvider.class);
        schedulerProvider = mock(SchedulerProvider.class);
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

        when(cacheProvider.saveWeather(any())).thenReturn(Observable.just(null));

        when(schedulerProvider.mainThread()).thenReturn(Schedulers.immediate());
        when(schedulerProvider.backgroundThread()).thenReturn(Schedulers.immediate());

        weatherInteractor = new WeatherInteractorImpl(cacheProvider, weatherApiService, schedulerProvider, clock);
        weatherInteractor.clearMemoryAndDiskCache();
    }

    @Test
    public void stage1_testHitsMemoryCache() {
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(expectedResult));
        when(cacheProvider.getWeather()).thenReturn(Observable.just(null));
        when(clock.millis()).thenReturn(0L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(Collections.singletonList(expectedResult));

        when(cacheProvider.getWeather()).thenReturn(Observable.just(nonExpectedResult));
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(nonExpectedResult));

        // must load data from Memory before checking cache or Network, cause the answer is there
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(Collections.singletonList(expectedResult));
    }

    @Test
    public void stage2_testHitsDiskCache() {
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(expectedResult));
        when(cacheProvider.getWeather()).thenReturn(Observable.just(null));
        when(clock.millis()).thenReturn(0L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(Collections.singletonList(expectedResult));

        weatherInteractor.clearMemoryCache();
        when(cacheProvider.getWeather()).thenReturn(Observable.just(expectedResult));
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(nonExpectedResult));

        // must load data from Cache after checking Memory which is null
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(Collections.singletonList(expectedResult));
    }

    @Test
    public void stage3_testCacheExpiry() {
        when(weatherApiService.loadWeather(any(String.class))).thenReturn(Observable.just(newExpectedResult));
        when(cacheProvider.getWeather()).thenReturn(Observable.just(expectedResult));
        when(clock.millis()).thenReturn(0L);

        // load weather from Cache but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(Collections.singletonList(expectedResult));

        when(clock.millis()).thenReturn(Constants.STALE_MS - 1);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(Collections.singletonList(expectedResult));

        when(clock.millis()).thenReturn(Constants.STALE_MS);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberThird = new TestSubscriber<>();
        weatherInteractor.loadWeather("Berlin").subscribe(testSubscriberThird);
        testSubscriberThird.assertNoErrors();
        testSubscriberThird.assertReceivedOnNext(Collections.singletonList(newExpectedResult));
    }

}