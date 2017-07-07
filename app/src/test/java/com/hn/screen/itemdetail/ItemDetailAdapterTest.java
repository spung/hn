package com.hn.screen.itemdetail;

import android.support.v7.widget.RecyclerView;

import com.hn.data.Item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Single;

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
        when(mItemDetailViewModel.bind()).thenReturn(Single.<List<Item>>never());
    }

    @Test
    public void testItemViewTypeValues() {
        ItemDetailAdapter adapter = new ItemDetailAdapter(mItemDetailViewModel);
        assertEquals(ItemDetailAdapter.VIEW_TYPE_ITEM_DETAIL, adapter.getItemViewType(0));
        assertEquals(ItemDetailAdapter.VIEW_TYPE_COMMENT, adapter.getItemViewType(10));
    }
}
