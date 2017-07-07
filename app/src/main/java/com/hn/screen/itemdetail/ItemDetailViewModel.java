package com.hn.screen.itemdetail;

import com.hn.data.Item;
import com.hn.shared.CommentsProvider;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailViewModel {
    private CommentsProvider mCommentsProvider;

    public ItemDetailViewModel(CommentsProvider commentsProvider) {
        mCommentsProvider = commentsProvider;
    }

    public Single<List<Item>> bind() {
        return mCommentsProvider.bind();
    }
}
