package com.hn.screen.itemdetail;

import com.hn.data.Item;

import io.reactivex.Observable;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailViewModel {
    private CommentsProvider mCommentsProvider;
    private Item mItem;

    public ItemDetailViewModel(Item item, CommentsProvider commentsProvider) {
        mCommentsProvider = commentsProvider;
        mItem = item;
    }

    public String getItemTitle() {
        return mItem.getTitle();
    }

    public Observable<Item> bind() {
        return mCommentsProvider.bind();
    }
}
