package com.hn.shared;


import com.crashlytics.android.Crashlytics;

import timber.log.Timber;

/**
 * Created by stevenpungdumri on 7/2/17.
 */

public class CrashlyticsTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        Crashlytics.log(priority, tag, message);
        Crashlytics.logException(t);
    }
}
