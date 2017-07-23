package com.hn.screen.itemdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hn.R;
import com.hn.data.Item;
import com.hn.network.ApiClient;
import com.hn.shared.BaseActivity;
import com.hn.shared.ResHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailActivity extends BaseActivity {
    public static final String EXTRA_ITEM = ItemDetailActivity.class.getName() + ".item";

    private ItemDetailViewModel mItemDetailViewModel;

    @Inject ApiClient mApiClient;

    @BindView(R.id.comments) RecyclerView mCommentsRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        Item item = getIntent().getParcelableExtra(EXTRA_ITEM);

        mItemDetailViewModel = new ItemDetailViewModel(item, new CommentsProvider(mApiClient, item));
//        getSupportActionBar().setTitle(mItemDetailViewModel.getItemTitle());

        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentsRecyclerView.setAdapter(new ItemDetailAdapter(mItemDetailViewModel));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mCommentsRecyclerView.getContext(),
            DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ResHelper.getDrawable(this, R.drawable.divider));
        mCommentsRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
