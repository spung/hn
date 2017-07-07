package com.hn.network;

import com.hn.data.Item;
import com.hn.shared.MockItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by stevenpungdumri on 6/28/17.
 */

public class MockApiClientModule implements ApiClient {
    private List<Long> mIds;

    public MockApiClientModule(List<Long> ids) {
        mIds = ids;
    }

    public MockApiClientModule() {}

    @Override
    public Observable<List<Long>> getTopItemIds() {
        return Observable.just(mIds);
    }

    @Override
    public Observable<Item> getItems(List<Long> ids) {
        return Observable.fromIterable(MockItem.createMockItems(ids));
    }

    @Override
    public Observable<Item> getComments(Item item) {
        return Observable.just(MockItem.createMockItem(0));
    }


}
