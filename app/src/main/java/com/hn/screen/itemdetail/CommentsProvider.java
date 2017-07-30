package com.hn.screen.itemdetail;

import com.hn.data.Item;
import com.hn.network.ApiClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class CommentsProvider {
    private ApiClient mApiClient;
    private Item mItem;

    public CommentsProvider(ApiClient apiClient, Item item) {
        mItem = item;
        mApiClient = apiClient;
    }

    public Observable<Item> bind() {
        if (!mItem.getComments().isEmpty()) {
            return Observable.fromIterable(mItem.getComments());
        }

        return mApiClient.getComments(mItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
