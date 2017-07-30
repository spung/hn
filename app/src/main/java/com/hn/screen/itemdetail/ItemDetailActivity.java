package com.hn.screen.itemdetail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hn.R;
import com.hn.data.Item;
import com.hn.network.ApiClient;
import com.hn.shared.BaseActivity;
import com.hn.shared.EventTracker;
import com.hn.shared.EventTypes;
import com.hn.shared.FontUtil;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailActivity extends BaseActivity {
    public static final String EXTRA_ITEM = ItemDetailActivity.class.getName() + ".item";
    private static final String LAYOUT_MANAGER = "layout_manager";
    private static final String ADAPTER_DATASET = "adapter_dataset";

    private ItemDetailViewModel mItemDetailViewModel;

    @Inject ApiClient mApiClient;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.tabLayout) TabLayout mTabLayout;
    @BindView(R.id.viewPager) ViewPager mViewPager;
    @BindView(R.id.openInBrowserFAB) FloatingActionButton mFloatingActionButton;

    @BindView(R.id.author) TextView mAuthorTextView;
    private ItemDetailPageAdapter mPageAdapter;
    private Item mItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        mItem = getIntent().getParcelableExtra(EXTRA_ITEM);
        mItemDetailViewModel = new ItemDetailViewModel(mItem, new CommentsProvider(mApiClient, mItem));
        mItemDetailViewModel.setLauncher(this);
        mPageAdapter = new ItemDetailPageAdapter(mItemDetailViewModel, mItem);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        EventTracker.trackItemEvent(EventTypes.VIEW_ITEM_COMMENTS, mItem); // capture the default to comments tab

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                if (position == ItemDetailPageAdapter.COMMENT_POSITION) {
                    EventTracker.trackItemEvent(EventTypes.VIEW_ITEM_COMMENTS, mItem);
                    mFloatingActionButton.hide();
                } else if (position == ItemDetailPageAdapter.DETAILS_POSITION) {
                    EventTracker.trackItemEvent(EventTypes.VIEW_ITEM_DETAILS, mItem);
                    if (!TextUtils.isEmpty(mItem.getUrl())) {
                        mFloatingActionButton.show();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        Typeface typeface = FontUtil.getDefaultFont(this);
        initCollapsingToolbar(mItem.getTitle(), R.drawable.back, typeface, typeface);

        initItemDetailViews(mItem);
    }

    @OnClick(R.id.openInBrowserFAB) void onOpenInBrowserFABClicked() {
        mItemDetailViewModel.onOpenInBrowserClicked();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mItemDetailViewModel.onShareClicked();
        EventTracker.trackItemEvent(EventTypes.CLICK_ITEM_SHARE, mItem);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mPageAdapter.onBackPressed(mViewPager.getCurrentItem())) {
            super.onBackPressed();
        }
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
