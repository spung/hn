package com.hn.shared;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hn.data.Item;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by stevenpungdumri on 7/30/17.
 */

public class EventTracker {
    private static final String TAG = EventTracker.class.getSimpleName();

    public static void trackEvent(String eventType) {
        if (App.isDebugMode()) return;

        Answers.getInstance().logCustom(new CustomEvent(eventType));
        FirebaseAnalytics.getInstance(App.getAppContext()).logEvent(eventType, null);

        Timber.tag(TAG);
        Timber.d("Logging %s", eventType);
    }

    public static void trackItemEvent(String eventType, Item item) {
        trackEvent(eventType, getItemDetails(item));
    }

    private static void trackEvent(String eventType, List<Pair<String, String>> data) {
        if (App.isDebugMode()) return;

        CustomEvent customEvent = new CustomEvent(eventType);

        Bundle bundle = new Bundle();
        for (Pair<String, String> part : data) {
            bundle.putString(part.first, part.second);
            customEvent.putCustomAttribute(part.first, part.second);
        }
        FirebaseAnalytics.getInstance(App.getAppContext()).logEvent(eventType, bundle);
        Answers.getInstance().logCustom(customEvent);

        Timber.tag(TAG);
        Timber.d("Logging %s with data: %s", eventType, data.toString());
    }

    private static List<Pair<String, String>> getItemDetails(Item item) {
        List<Pair<String, String>> details = new ArrayList<>();
        details.add(new Pair<>(EventAttributes.ITEM_ID, item.getId() + ""));
        if (!TextUtils.isEmpty(item.getUrl())) {
            details.add(new Pair<>(EventAttributes.ITEM_URL, item.getUrl()));
        }

        if (!TextUtils.isEmpty(item.getText())) {
            details.add(new Pair<>(EventAttributes.ITEM_TEXT, item.getText()));
        }

        details.add(new Pair<>(EventAttributes.ITEM_HN_URL, "https://news.ycombinator.com/item?id=" + item.getId()));
        details.add(new Pair<>(EventAttributes.ITEM_HN_JSON_URL,
            String.format("https://hacker-news.firebaseio.com/v0/item/%d.json?print=pretty", item.getId())));

        return details;
    }
}
