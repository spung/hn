package com.hn.screen.items;

import android.content.Intent;

import com.hn.data.Item;
import com.hn.screen.itemdetail.ItemDetailActivity;
import com.hn.shared.Launcher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by stevenpungdumri on 6/26/17.
 */

public class TopItemsViewModel {
    private TopItemsProviderModule mTopItemsProviderModule;
    private Launcher mLauncher;
    private ClearItemsListener mClearItemsListener;

    @Inject
    public TopItemsViewModel(TopItemsProviderModule topItemsProviderModule) {
        mTopItemsProviderModule = topItemsProviderModule;
    }

    public void setClearItemsListener(ClearItemsListener clearItemsListener) {
        mClearItemsListener = clearItemsListener;
    }

    public void setLauncher(Launcher launcher) {
        mLauncher = launcher;
    }

    public Observable<List<Item>> bindItems() {
        return mTopItemsProviderModule.bind();
    }

    public void reachedEndOfList() {
        mTopItemsProviderModule.fetchMoreItems();
    }

    public void launchItemDetail(Item item) {
        Intent intent = new Intent(mLauncher.getContext(), ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.EXTRA_ITEM, item);
        mLauncher.launchActivity(intent);
    }

    public void setLastIndex(int index) {
        mTopItemsProviderModule.setIndex(index);
    }

    public void onSwipedToRefresh() {
        if (mClearItemsListener != null && mTopItemsProviderModule.clearCacheAndReload()) {
            mClearItemsListener.clearItems();
        }
    }

    public interface ClearItemsListener {
        void clearItems();
    }
}
