package com.hn.screen.items;

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
    private Item mItem;

    @BindView(R.id.title) TextView mTitleTextView;

    public ItemViewHolder(View itemView, final ItemClickListener clickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClicked(mItem);
            }
        });
    }

    public void bindItem(Item item) {
        mItem = item;
        mTitleTextView.setText(mItem.getTitle());
    }
}
