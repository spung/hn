package com.hn.shared;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by stevenpungdumri on 6/29/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected AppComponent getAppComponent() {
        return getApp().getAppComponent();
    }

    private App getApp() {
        return (App) getApplication();
    }
}
