package com.hn.screen.itemdetail;

import android.support.v7.widget.RecyclerView;

import com.hn.data.Item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by stevenpungdumri on 7/6/17.
 */

public class ItemDetailAdapterTest {

    @Mock ItemDetailViewModel mItemDetailViewModel;
    @Mock RecyclerView mRecyclerView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(mItemDetailViewModel.bind()).thenReturn(Observable.<Item>never());
    }

    @Test
    public void testItemViewTypeValues() {
        CommentsAdapter adapter = new CommentsAdapter(mItemDetailViewModel);
        assertEquals(CommentsAdapter.VIEW_TYPE_ITEM_DETAIL, adapter.getItemViewType(0));
        assertEquals(CommentsAdapter.VIEW_TYPE_COMMENT, adapter.getItemViewType(10));
    }
}
