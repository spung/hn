package com.hn.shared;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stevenpungdumri on 6/29/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements Launcher {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Timber.tag(this.getClass().getSimpleName());
        FirebaseAnalytics.getInstance(this).setCurrentScreen(this, this.getClass().getSimpleName(), null);
    }

    protected AppComponent getAppComponent() {
        return getApp().getAppComponent();
    }

    @Override
    public void launchActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private App getApp() {
        return (App) getApplication();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void launchShareIntent(String link) {
        startActivity(ShareCompat.IntentBuilder.from(this).setType("text/plain").setText(link).createChooserIntent());
    }

    @Override
    public void launchBrowserIntent(String link) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            EventTracker.trackEvent(EventTypes.SCREEN_ROTATED_TO_LANDSCAPE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            EventTracker.trackEvent(EventTypes.SCREEN_ROTATED_TO_PORTRAIT);
        }
    }
}
