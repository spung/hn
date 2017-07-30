package com.hn.screen.itemdetail;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hn.R;
import com.hn.data.Item;

/**
 * Created by stevenpungdumri on 7/23/17.
 */

public class ItemDetailPageAdapter extends PagerAdapter {
    private static final int COMMENT_POSITION = 0;
    private Item mItem;
    private ItemDetailViewModel mViewModel;
    private final String[] mTitles = new String[] { "COMMENTS", "DETAILS" };

    public ItemDetailPageAdapter(ItemDetailViewModel viewModel, Item item) {
        mItem = item;
        mViewModel = viewModel;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        ViewGroup layout;
        if (position == COMMENT_POSITION) {
            layout = (ViewGroup) inflater.inflate(R.layout.layout_item_detail_comments_page, container, false);
            ((ItemCommentsView) layout).setItem(mViewModel);
        } else {
            layout = (ViewGroup) inflater.inflate(R.layout.layout_item_detail_content_page, container, false);
            ((ItemDetailsContentView) layout).setItem(mItem);
        }

        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}