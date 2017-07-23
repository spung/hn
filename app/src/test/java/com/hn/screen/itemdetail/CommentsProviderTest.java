package com.hn.screen.itemdetail;

import com.hn.data.Item;
import com.hn.network.ApiClient;
import com.hn.shared.MockItem;
import com.hn.shared.TestScheduler;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by stevenpungdumri on 7/6/17.
 */

public class CommentsProviderTest {

    @Mock ApiClient mApiClient;

    private CommentsProvider mCommentsProvider;
    private Item mItem;

    @ClassRule
    public static final TestScheduler schedulers = new TestScheduler();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mItem = MockItem.createMockItem(0);
        mCommentsProvider = new CommentsProvider(mApiClient, mItem);
        when(mApiClient.getComments(mItem)).thenReturn(Observable.fromIterable(MockItem.createMockItems(2)));
    }

    @Test
    public void bindWithItemWithExitingComments() {
        mItem.setComments(MockItem.createMockItems(2));
        mCommentsProvider.bind();
        verify(mApiClient, never()).getComments(mItem);
    }

    @Test
    public void bindWithItemWithoutExitingComments() {
        mItem.setComments(new ArrayList<Item>());
        mCommentsProvider.bind();
        verify(mApiClient).getComments(mItem);
    }
}
