package com.hn.network;

import com.hn.data.Item;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by stevenpungdumri on 6/28/17.
 */

public interface ApiClient {
    Observable<List<Long>> getTopItemIds();

    Observable<Item> getItems(List<Long> ids);

    Observable<Item> getComments(Item item);
}
