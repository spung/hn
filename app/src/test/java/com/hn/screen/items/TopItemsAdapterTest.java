package com.hn.screen.items;

import com.hn.data.Item;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by stevenpungdumri on 7/1/17.
 */

public class TopItemsAdapterTest {

    @Mock TopItemsViewModel mTopItemsViewModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(mTopItemsViewModel.bindItems()).thenReturn(Observable.<List<Item>>empty());
    }

    @Test
    public void initializingAdapterCallsReachedEndOfList() {
        TopItemsAdapter topItemsAdapter = new TopItemsAdapter(mTopItemsViewModel);
        verify(mTopItemsViewModel).reachedEndOfList();
    }

    @Test
    public void bindingLoadingItemViewTypeCallsReachedEndOfList() {
        TopItemsAdapter topItemsAdapter = new TopItemsAdapter(mTopItemsViewModel);
        topItemsAdapter.onBindViewHolder(null, 0);
        verify(mTopItemsViewModel, times(2)).reachedEndOfList();
    }
}
