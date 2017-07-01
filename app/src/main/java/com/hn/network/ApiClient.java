package com.hn.network;

import com.hn.data.Item;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by stevenpungdumri on 6/28/17.
 */

public interface ApiClient {
    Observable<List<Long>> getTopItemIds();

    Single<List<Item>> getItems(List<Long> ids);
}
