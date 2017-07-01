package com.hn.items;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hn.R;
import com.hn.data.Item;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 6/25/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title) TextView mTitleTextView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindItem(Item item) {
        mTitleTextView.setText(item.getTitle());
    }
}
