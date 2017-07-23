package com.hn.screen.itemdetail;

import com.hn.data.Item;
import com.hn.shared.MockItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Created by stevenpungdumri on 7/6/17.
 */

public class ItemDetailViewModelTest {
    private ItemDetailViewModel mItemDetailViewModel;
    private Item mItem;

    @Mock CommentsProvider mCommentsProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mItem = MockItem.createMockItem(1);
        mItemDetailViewModel = new ItemDetailViewModel(mItem, mCommentsProvider);
    }

    @Test
    public void getItemsObservableDelegatesToProvider() {
        mItemDetailViewModel.bind();
        verify(mCommentsProvider).bind();
    }

    @Test
    public void getItemsTitle() {
        assertEquals(mItemDetailViewModel.getItemTitle(), mItem.getTitle());
    }
}
