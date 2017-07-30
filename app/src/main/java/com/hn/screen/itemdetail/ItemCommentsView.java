package com.hn.screen.itemdetail;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hn.R;
import com.hn.shared.ResHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 7/23/17.
 */

public class ItemCommentsView extends FrameLayout {

    @BindView(R.id.comments) RecyclerView mCommentsRecyclerView;
    @BindView(R.id.progressBar) View mProgressBarView;
    @BindView(R.id.noCommentsTextView) View mNoCommentsTextView;

    public ItemCommentsView(@NonNull Context context) {
        super(context);
        init();
    }

    public ItemCommentsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemCommentsView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setItem(ItemDetailViewModel viewModel) {
        Context context = getContext();

        if (viewModel.hasComments()) {
            mNoCommentsTextView.setVisibility(GONE);
        } else {
            mProgressBarView.setVisibility(GONE);
        }

        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        // TODO: implement save instance states for screen rotations so the user doesn't lose their place in comments
//        ArrayList restoredData = savedInstanceState == null ? null : savedInstanceState.getParcelableArrayList(ADAPTER_DATASET);
        ArrayList restoredData = null;
        mCommentsRecyclerView.setAdapter(new CommentsAdapter(viewModel, restoredData,
            new CommentsAdapter.FirstItemListener() {
                @Override
                public void onFirstItemFetched() {
                    mProgressBarView.setVisibility(View.GONE);
                }
            }));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mCommentsRecyclerView.getContext(),
            DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ResHelper.getDrawable(context, R.drawable.divider));
        mCommentsRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void init() {
        ButterKnife.bind(inflate(getContext(), R.layout.layout_item_detail_comments, this));
    }
}
