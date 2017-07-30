package com.hn.shared;

import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

/**
 * Created by stevenpungdumri on 7/30/17.
 */

public class FirebaseTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        FirebaseCrash.logcat(priority, tag, message);
    }
}
