package com.hn.shared;

import com.hn.data.Item;
import com.hn.network.ApiClient;

import java.util.List;

import io.reactivex.Single;
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

    public Single<List<Item>> bind() {
        if (mItem.getComments() != null && !mItem.getComments().isEmpty()) {
            return Single.just(mItem.getComments());
        }

        return mApiClient.getComments(mItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toList();
    }
}
