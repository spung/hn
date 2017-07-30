package com.hn.screen.itemdetail;

import android.text.TextUtils;

import com.hn.data.Item;
import com.hn.shared.EventTracker;
import com.hn.shared.Launcher;

import io.reactivex.Observable;

import static com.hn.shared.EventTypes.CLICK_OPEN_IN_BROWSER;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailViewModel {
    private CommentsProvider mCommentsProvider;
    private Item mItem;
    private Launcher mLauncher;

    public ItemDetailViewModel(Item item, CommentsProvider commentsProvider) {
        mCommentsProvider = commentsProvider;
        mItem = item;
    }

    public boolean hasComments() {
        return !mItem.getKids().isEmpty();
    }

    public void setLauncher(Launcher launcher) {
        mLauncher = launcher;
    }

    public String getItemTitle() {
        return mItem.getTitle();
    }

    public Observable<Item> bind() {
        return mCommentsProvider.bind();
    }

    public void onShareClicked() {
        if (mLauncher != null) {
            if (TextUtils.isEmpty(mItem.getUrl())) {
                mLauncher.launchShareIntent("https://news.ycombinator.com/item?id=" + mItem.getId());
            } else {
                mLauncher.launchShareIntent(mItem.getUrl());
            }
        }
    }

    public void onOpenInBrowserClicked() {
        EventTracker.trackItemEvent(CLICK_OPEN_IN_BROWSER, mItem);
        mLauncher.launchBrowserIntent(mItem.getUrl());
    }
}
