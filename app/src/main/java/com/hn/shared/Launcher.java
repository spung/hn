package com.hn.shared;

import android.content.Context;
import android.content.Intent;

/**
 * Created by stevenpungdumri on 7/6/17.
 */

public interface Launcher {
    void launchActivity(Intent intent);
    Context getContext();
    void launchShareIntent(String link);
    void launchBrowserIntent(String link);
}
