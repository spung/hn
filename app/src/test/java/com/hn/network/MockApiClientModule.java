package com.hn.network;

import com.hn.data.HNItemType;
import com.hn.data.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by stevenpungdumri on 6/28/17.
 */

public class MockApiClientModule implements ApiClient {
    private List<Long> mIds;

    public MockApiClientModule(List<Long> ids) {
        mIds = ids;
    }

    public Observable<List<Long>> getTopItemIds() {
        return Observable.just(mIds);
    }

    public Single<List<Item>> getItems(List<Long> ids) {
        List<Item> items = new ArrayList<>();
        for(Long id : ids) {
            items.add(new Item(
                id,
                false,
                HNItemType.values()[0],
                "test",
                0,
                "text",
                false,
                1,
                1,
                new long[] {10},
                "test",
                1,
                "test",
                new long[] {10},
                1
            ));
        }
        return Single.just(items);
    }
}
