package com.hn.screen.itemdetail;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.hn.R;
import com.hn.data.Item;
import com.hn.shared.ResHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder {
    private Item mItem;

    @BindView(R.id.author) TextView mAuthorTextView;
    @BindView(R.id.text) TextView mTextTextView;

    public CommentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Item item) {
        mItem = item;
        mAuthorTextView.setText(mItem.getBy());
        mTextTextView.setText(Html.fromHtml(item.getText()).toString().trim());
        itemView.setPadding(
            ResHelper.getDimen(itemView.getContext(), R.dimen.indentation_spacing) * item.getIndentation(),
            itemView.getPaddingTop(),
            itemView.getPaddingRight(),
            itemView.getPaddingBottom()
        );
    }
}
