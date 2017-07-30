package com.hn.screen.items;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hn.R;
import com.hn.shared.BaseActivity;
import com.hn.shared.ResHelper;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 6/25/17.
 */

public class ItemsActivity extends BaseActivity {
    private static final String LAYOUT_MANAGER = "layout_manager";
    private static final String ADAPTER_DATASET = "adapter_dataset";

    @Inject TopItemsViewModel mTopItemsViewModel;

    @BindView(R.id.topItems) RecyclerView mTopItemsRecyclerView;
    @BindView(R.id.toolbarTitle) TextView mToolbarTitle;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTopItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTopItemsViewModel.setLauncher(this);
        ArrayList previousDataset = savedInstanceState == null ?
            null : savedInstanceState.getParcelableArrayList(ADAPTER_DATASET);
        TopItemsAdapter adapter = new TopItemsAdapter(mTopItemsViewModel, previousDataset);
        mTopItemsRecyclerView.setAdapter(adapter);
        mTopItemsViewModel.setClearItemsListener(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mTopItemsRecyclerView.getContext(),
            DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ResHelper.getDrawable(this, R.drawable.divider));
        mTopItemsRecyclerView.addItemDecoration(dividerItemDecoration);

        mSwipeRefreshLayout.setColorSchemeColors(
            ResHelper.getColor(this, R.color.colorAccent),
            ResHelper.getColor(this, R.color.colorPrimary),
            ResHelper.getColor(this, R.color.colorPrimaryDark)
        );
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTopItemsViewModel.onSwipedToRefresh();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LAYOUT_MANAGER, mTopItemsRecyclerView.getLayoutManager().onSaveInstanceState());
        TopItemsAdapter adapter = (TopItemsAdapter) mTopItemsRecyclerView.getAdapter();
        adapter.onSaveInstanceState(outState, ADAPTER_DATASET);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTopItemsRecyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_MANAGER));
    }
}
