package com.hn.shared;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.hn.BuildConfig;
import com.hn.screen.items.TopItemsProviderModule;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by stevenpungdumri on 6/27/17.
 */

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashlyticsTree());
        }

        mAppComponent = DaggerAppComponent.builder()
            .topItemsProviderModule(new TopItemsProviderModule(BuildConfig.ItemsPerPage))
            .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
