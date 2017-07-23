package com.hn.screen.itemdetail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hn.R;
import com.hn.data.Item;
import com.hn.network.ApiClient;
import com.hn.shared.BaseActivity;
import com.hn.shared.FontUtil;
import com.hn.shared.ResHelper;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailActivity extends BaseActivity {
    public static final String EXTRA_ITEM = ItemDetailActivity.class.getName() + ".item";
    private static final String LAYOUT_MANAGER = "layout_manager";
    private static final String ADAPTER_DATASET = "adapter_dataset";

    private ItemDetailViewModel mItemDetailViewModel;

    @Inject ApiClient mApiClient;

    @BindView(R.id.comments) RecyclerView mCommentsRecyclerView;
    @BindView(R.id.progressBar) View mProgressBarView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.author) TextView mAuthorTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        Item item = getIntent().getParcelableExtra(EXTRA_ITEM);

        mItemDetailViewModel = new ItemDetailViewModel(item, new CommentsProvider(mApiClient, item));

        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList restoredData = savedInstanceState == null ? null : savedInstanceState.getParcelableArrayList(ADAPTER_DATASET);
        mCommentsRecyclerView.setAdapter(new CommentsAdapter(mItemDetailViewModel, restoredData,
            new CommentsAdapter.FirstItemListener() {
                @Override
                public void onFirstItemFetched() {
                    mProgressBarView.setVisibility(View.GONE);
                }
            }));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mCommentsRecyclerView.getContext(),
            DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ResHelper.getDrawable(this, R.drawable.divider));
        mCommentsRecyclerView.addItemDecoration(dividerItemDecoration);

        Typeface typeface = FontUtil.getDefaultFont(this);
        initCollapsingToolbar(item.getTitle(), R.drawable.back, typeface, typeface);

        initItemDetailViews(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(LAYOUT_MANAGER, mCommentsRecyclerView.getLayoutManager().onSaveInstanceState());
        ((CommentsAdapter) mCommentsRecyclerView.getAdapter()).onSaveInstanceState(outState, ADAPTER_DATASET);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCommentsRecyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_MANAGER));
    }

    private void initItemDetailViews(Item item) {
        mAuthorTextView.setText(item.getBy());
    }

    protected void initCollapsingToolbar(String title, int homeIcon, Typeface collapsedTypeface, Typeface expandedTypeface) {
        setSupportActionBar(mToolbar);
        mCollapsingToolbarLayout.setTitle(title);
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(collapsedTypeface);
        mCollapsingToolbarLayout.setExpandedTitleTypeface(expandedTypeface);

        mToolbar.setNavigationIcon(homeIcon);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
