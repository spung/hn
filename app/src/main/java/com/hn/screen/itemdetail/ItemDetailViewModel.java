package com.hn.screen.itemdetail;

import com.hn.data.Item;
import com.hn.shared.CommentsProvider;

import io.reactivex.Observable;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailViewModel {
    private CommentsProvider mCommentsProvider;

    public ItemDetailViewModel(CommentsProvider commentsProvider) {
        mCommentsProvider = commentsProvider;
    }

    public Observable<Item> bind() {
        return mCommentsProvider.bind();
    }
}
