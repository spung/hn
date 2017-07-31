package com.hn.shared;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.hn.BuildConfig;
import com.hn.R;
import com.hn.screen.items.TopItemsProviderModule;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by stevenpungdumri on 6/27/17.
 */

public class App extends Application {
    private static Application sApp;
    private AppComponent mAppComponent;

    public App() {
        sApp = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        Crashlytics crashlyticsKit = new Crashlytics.Builder()
            .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build();

        Fabric.with(this, crashlyticsKit);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashlyticsTree());
            Timber.plant(new FirebaseTree());
        }

        mAppComponent = DaggerAppComponent.builder()
            .topItemsProviderModule(new TopItemsProviderModule(BuildConfig.ItemsPerPage))
            .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static Context getAppContext() {
        return sApp.getApplicationContext();
    }

    public static boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }
}
