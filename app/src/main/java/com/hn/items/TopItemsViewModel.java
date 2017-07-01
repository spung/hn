package com.hn.items;

import com.hn.data.Item;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by stevenpungdumri on 6/26/17.
 */

public class TopItemsViewModel {
    private TopItemsProviderModule mTopItemsProviderModule;

    @Inject
    public TopItemsViewModel(TopItemsProviderModule topItemsProviderModule) {
        mTopItemsProviderModule = topItemsProviderModule;
    }

    public Observable<List<Item>> bindItems() {
        return mTopItemsProviderModule.bind();
    }

    public void reachedEndOfList() {
        mTopItemsProviderModule.fetchMoreItems();
    }
}
