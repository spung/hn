package com.hn.shared;

import android.app.Application;

import com.hn.BuildConfig;
import com.hn.items.TopItemsProviderModule;

/**
 * Created by stevenpungdumri on 6/27/17.
 */

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
            .topItemsProviderModule(new TopItemsProviderModule(BuildConfig.ItemsPerPage))
            .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
