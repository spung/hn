package com.hn.items;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hn.R;
import com.hn.shared.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 6/25/17.
 */

public class ItemsActivity extends BaseActivity {

    @Inject TopItemsViewModel mTopItemsViewModel;

    @BindView(R.id.topItems) RecyclerView mTopItemsRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTopItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTopItemsRecyclerView.setAdapter(new TopItemsAdapter(mTopItemsViewModel));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mTopItemsRecyclerView.getContext(),
            DividerItemDecoration.VERTICAL);
        mTopItemsRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
