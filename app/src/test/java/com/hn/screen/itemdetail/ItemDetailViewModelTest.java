package com.hn.screen.itemdetail;

import com.hn.shared.CommentsProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by stevenpungdumri on 7/6/17.
 */

public class ItemDetailViewModelTest {
    private ItemDetailViewModel mItemDetailViewModel;

    @Mock CommentsProvider mCommentsProvider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mItemDetailViewModel = new ItemDetailViewModel(mCommentsProvider);
    }

    @Test
    public void getItemsObservableDelegatesToProvider() {
        mItemDetailViewModel.bind();
        verify(mCommentsProvider).bind();
    }
}
