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

    @BindView(R.id.index) TextView mIndexTextView;
    @BindView(R.id.title) TextView mTitleTextView;
    @BindView(R.id.author) TextView mAuthor;
    @BindView(R.id.score) TextView mScore;
    @BindView(R.id.commentCount) TextView mCommentCount;
    @BindView(R.id.commentSection) View mCommentSection;

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

    public void bindItem(Item item, int position) {
        mItem = item;
        mTitleTextView.setText(mItem.getTitle());
        mIndexTextView.setText("" + position);
        mAuthor.setText(mItem.getBy());
        mScore.setText("+" + mItem.getScore());

        mCommentCount.setText(mItem.getDescendants() + "");
        if (mItem.getDescendants() > 0) {
            mCommentSection.setAlpha(1.0f);
        } else {
            mCommentSection.setAlpha(0.25f);
        }
    }
}
