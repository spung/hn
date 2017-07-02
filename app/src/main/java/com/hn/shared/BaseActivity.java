package com.hn.shared;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

/**
 * Created by stevenpungdumri on 6/29/17.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Timber.tag(this.getClass().getSimpleName());
    }

    protected AppComponent getAppComponent() {
        return getApp().getAppComponent();
    }

    private App getApp() {
        return (App) getApplication();
    }
}
