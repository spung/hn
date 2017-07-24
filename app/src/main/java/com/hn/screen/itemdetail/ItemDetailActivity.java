package com.hn.screen.itemdetail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hn.R;
import com.hn.data.Item;
import com.hn.network.ApiClient;
import com.hn.shared.BaseActivity;
import com.hn.shared.FontUtil;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

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

    @Inject ApiClient mApiClient;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.tabLayout) TabLayout mTabLayout;
    @BindView(R.id.viewPager) ViewPager mViewPager;

    @BindView(R.id.author) TextView mAuthorTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        Item item = getIntent().getParcelableExtra(EXTRA_ITEM);

        mViewPager.setAdapter(new ItemDetailPageAdapter(
            new ItemDetailViewModel(item, new CommentsProvider(mApiClient, item)), item));
        mTabLayout.setupWithViewPager(mViewPager);

        Typeface typeface = FontUtil.getDefaultFont(this);
        initCollapsingToolbar(item.getTitle(), R.drawable.back, typeface, typeface);

        initItemDetailViews(item);
    }

    // TODO: implement save instance states for screen rotations so the user doesn't lose their place in comments
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putParcelable(LAYOUT_MANAGER, mCommentsRecyclerView.getLayoutManager().onSaveInstanceState());
//        ((CommentsAdapter) mCommentsRecyclerView.getAdapter()).onSaveInstanceState(outState, ADAPTER_DATASET);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mCommentsRecyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(LAYOUT_MANAGER));
//    }

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
