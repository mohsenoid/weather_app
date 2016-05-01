package com.mirhoseini.weatherapp;

import com.mirhoseini.weatherapp.core.model.Clock;
import com.mirhoseini.weatherapp.core.model.WeatherInteractor;
import com.mirhoseini.weatherapp.core.service.WeatherNetworkService;
import com.mirhoseini.weatherapp.core.service.model.WeatherCurrent;
import com.mirhoseini.weatherapp.core.service.model.WeatherForecast;
import com.mirhoseini.weatherapp.core.service.model.WeatherMix;
import com.mirhoseini.weatherapp.core.utils.Constants;
import com.mirhoseini.weatherapp.utils.AppCacher;
import com.mirhoseini.weatherapp.utils.AppScheduler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        application = WeatherApplication.class,
        sdk = 21)
public class WeatherInteractorTests {
    private WeatherInteractor mWeatherInteractor;
    private WeatherNetworkService mWeatherNetworkService;
    private AppCacher mAppCacher;
    private AppScheduler mAppScheduler;
    private Clock mClock;
    private WeatherMix mExpectedResult;
    private WeatherMix mNonExpectedResult;
    private WeatherMix mNewExpectedResult;

    @Before
    public void setup() {
        mWeatherNetworkService = mock(WeatherNetworkService.class);
        mAppCacher = mock(AppCacher.class);
        mAppScheduler = mock(AppScheduler.class);
        mClock = mock(Clock.class);

        mExpectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        mExpectedResult.getWeatherCurrent().setName("Berlin");
        mExpectedResult.getWeatherCurrent().setDt(0L);

        mNonExpectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        mNonExpectedResult.getWeatherCurrent().setName("Tehran");
        mNonExpectedResult.getWeatherCurrent().setDt(0L);

        mNewExpectedResult = new WeatherMix(new WeatherCurrent(), new WeatherForecast());
        mNewExpectedResult.getWeatherCurrent().setName("Berlin");
        mNewExpectedResult.getWeatherCurrent().setDt(1000L);

        when(mAppCacher.saveWeather(any())).thenReturn(Observable.just(null));

        when(mAppScheduler.mainThread()).thenReturn(Schedulers.immediate());
        when(mAppScheduler.backgroundThread()).thenReturn(Schedulers.immediate());

        mWeatherInteractor = new WeatherInteractor(mAppCacher, mWeatherNetworkService, mAppScheduler, mClock);
    }

    @Test
    public void testHitsMemoryCache() {
        when(mWeatherNetworkService.loadWeather(any(String.class))).thenReturn(Observable.just(mExpectedResult));
        when(mAppCacher.getWeather()).thenReturn(Observable.just(null));
        when(mClock.millis()).thenReturn(1000L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        mWeatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(mExpectedResult));

        when(mAppCacher.getWeather()).thenReturn(Observable.just(mNonExpectedResult));
        when(mWeatherNetworkService.loadWeather(any(String.class))).thenReturn(Observable.just(mNonExpectedResult));

        // must load data from Memory before checking cache or Network, cause the answer is there
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        mWeatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(mExpectedResult));
    }

    @Test
    public void testHitsDiskCache() {
        when(mWeatherNetworkService.loadWeather(any(String.class))).thenReturn(Observable.just(mExpectedResult));
        when(mAppCacher.getWeather()).thenReturn(Observable.just(null));
        when(mClock.millis()).thenReturn(1000L);

        // must load data from Network, cause Memory and disk cache are null
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        mWeatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(mExpectedResult));

        mWeatherInteractor.clearMemoryCache();
        when(mAppCacher.getWeather()).thenReturn(Observable.just(mExpectedResult));
        when(mWeatherNetworkService.loadWeather(any(String.class))).thenReturn(Observable.just(mNonExpectedResult));

        // must load data from Cache after checking Memory which is null
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        mWeatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(mExpectedResult));
    }

    @Test
    public void testCacheExpiry() {
        when(mWeatherNetworkService.loadWeather(any(String.class))).thenReturn(Observable.just(mNewExpectedResult));
        when(mAppCacher.getWeather()).thenReturn(Observable.just(mExpectedResult));
        when(mClock.millis()).thenReturn(1000L);

        // load weather from Cache but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberFirst = new TestSubscriber<>();
        mWeatherInteractor.loadWeather("Berlin").subscribe(testSubscriberFirst);
        testSubscriberFirst.assertNoErrors();
        testSubscriberFirst.assertReceivedOnNext(singletonList(mExpectedResult));

        when(mClock.millis()).thenReturn(Constants.STALE_MS * 1000 - 1);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberSecond = new TestSubscriber<>();
        mWeatherInteractor.loadWeather("Berlin").subscribe(testSubscriberSecond);
        testSubscriberSecond.assertNoErrors();
        testSubscriberSecond.assertReceivedOnNext(singletonList(mExpectedResult));

        when(mClock.millis()).thenReturn(Constants.STALE_MS * 1000);

        // load weather from Memory but is not expired yet
        TestSubscriber<WeatherMix> testSubscriberThird = new TestSubscriber<>();
        mWeatherInteractor.loadWeather("Berlin").subscribe(testSubscriberThird);
        testSubscriberThird.assertNoErrors();
        testSubscriberThird.assertReceivedOnNext(singletonList(mNewExpectedResult));
    }
}