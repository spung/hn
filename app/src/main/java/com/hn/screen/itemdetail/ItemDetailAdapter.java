package com.hn.screen.itemdetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hn.R;
import com.hn.data.Item;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailAdapter extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_ITEM_DETAIL = 0;
    public static final int VIEW_TYPE_COMMENT = 1;

    private List<Item> mItems;

    public ItemDetailAdapter(ItemDetailViewModel itemDetailViewModel) {
        itemDetailViewModel.bind().subscribe(new SingleObserver<List<Item>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onSuccess(@NonNull List<Item> items) {
                mItems = items;
                notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull Throwable e) {}
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW_TYPE_ITEM_DETAIL:
                return new ItemDetailViewHolder(layoutInflater.inflate(R.layout.layout_item_detail, parent, false));
            case VIEW_TYPE_COMMENT:
            default:
                return new CommentViewHolder(layoutInflater.inflate(R.layout.layout_comment, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = mItems.get(position);

        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM_DETAIL:
                ((ItemDetailViewHolder) holder).bindView(item);
                break;
            case VIEW_TYPE_COMMENT:
                ((CommentViewHolder) holder).bindView(item);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return VIEW_TYPE_ITEM_DETAIL;
        return VIEW_TYPE_COMMENT;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }
}
