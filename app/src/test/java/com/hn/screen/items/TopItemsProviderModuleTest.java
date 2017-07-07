package com.hn.screen.items;

import com.hn.data.Item;
import com.hn.network.ApiClient;
import com.hn.network.MockApiClientModule;
import com.hn.shared.TestScheduler;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;

/**
 * Created by stevenpungdumri on 6/27/17.
 */

public class TopItemsProviderModuleTest {

    private TopItemsProviderModule mTopItemsProviderModule;
    private int mItemsPerPage = 5;

    @ClassRule
    public static final TestScheduler schedulers = new TestScheduler();

    @Before
    public void setup() {
        List<Long> ids = new ArrayList<>();
        for (long i = 0; i < 100; i++) {
            ids.add(i);
        }
        ApiClient apiClient = new MockApiClientModule(ids);

        mTopItemsProviderModule = new TopItemsProviderModule(apiClient, mItemsPerPage);
    }

    @Test
    public void fetchingItemsPagesWithCorrectValue() {
        TestObserver<List<Item>> itemObserver = mTopItemsProviderModule.bind().test();

        mTopItemsProviderModule.fetchMoreItems();
        assertEquals(0, itemObserver.values().get(0).get(0).getId());
        assertEquals(mItemsPerPage, itemObserver.values().get(0).size());

        mTopItemsProviderModule.fetchMoreItems();
        assertEquals(mItemsPerPage, itemObserver.values().get(1).get(0).getId());
        assertEquals(mItemsPerPage, itemObserver.values().get(1).size());
    }
}
