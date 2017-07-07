package com.hn.screen.items;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by stevenpungdumri on 6/27/17.
 */

public class TopItemsViewModelTest {

    private TopItemsViewModel mTopItemsViewModel;

    @Mock
    TopItemsProviderModule mTopItemsProviderModule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mTopItemsViewModel = new TopItemsViewModel(mTopItemsProviderModule);
    }

    @Test
    public void getItemsObservableDelegatesToProvider() {
        mTopItemsViewModel.bindItems();
        verify(mTopItemsProviderModule).bind();
    }

    @Test
    public void reachedEndOfListFetchesMoreItems() {
        mTopItemsViewModel.reachedEndOfList();
        verify(mTopItemsProviderModule).fetchMoreItems();
    }
}
