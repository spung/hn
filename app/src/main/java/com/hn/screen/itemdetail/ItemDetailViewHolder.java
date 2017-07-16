package com.hn.screen.itemdetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hn.R;
import com.hn.data.Item;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailViewHolder extends RecyclerView.ViewHolder {
    private Item mItem;

    @BindView(R.id.title) TextView mTitleTextView;
    @BindView(R.id.author) TextView mAuthorTextView;
    @BindView(R.id.text) TextView mTextTextView;
    @BindView(R.id.url) TextView mUrlTextView;

    public ItemDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Item item) {
        mItem = item;
        mTitleTextView.setText(mItem.getTitle());
        mAuthorTextView.setText(mItem.getBy());
        mTextTextView.setText(mItem.getText());
        mUrlTextView.setText(mItem.getUrl());
    }
}
