package com.hn.screen.items;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hn.R;
import com.hn.data.Item;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by stevenpungdumri on 6/25/17.
 */

public class TopItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_SPINNER_THRESHOLD = 8;

    private static final int NORMAL_ITEM_TYPE = 0;
    private static final int LOADING_ITEM_TYPE = 1;

    private TopItemsViewModel mTopItemsViewModel;
    private List<Item> mItems;

    public TopItemsAdapter(TopItemsViewModel topItemsViewModel) {
        mTopItemsViewModel = topItemsViewModel;
        mItems = new ArrayList<>();
        mTopItemsViewModel.bindItems().subscribe(new Observer<List<Item>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(@NonNull List<Item> items) {
                int priorSize = mItems.size();
                mItems.addAll(items);
                notifyItemRangeInserted(priorSize, items.size());
            }

            @Override
            public void onError(@NonNull Throwable e) {}

            @Override
            public void onComplete() {}
        });

        mTopItemsViewModel.reachedEndOfList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case LOADING_ITEM_TYPE:
                return new LoadingItemViewHolder(layoutInflater.inflate(R.layout.layout_loading_item, parent, false));
            case NORMAL_ITEM_TYPE:
            default:
                return new ItemViewHolder(layoutInflater.inflate(R.layout.layout_item, parent, false), new ItemClickListener() {
                    @Override
                    public void onItemClicked(Item item) {
                        mTopItemsViewModel.launchItemDetail(item);
                    }
                });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         switch (getItemViewType(position)) {
            case LOADING_ITEM_TYPE:
                mTopItemsViewModel.reachedEndOfList();
                break;
            case NORMAL_ITEM_TYPE:
            default:
                ((ItemViewHolder) holder).bindItem(mItems.get(position), position + 1);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mItems.size()) {
            return LOADING_ITEM_TYPE;
        } else {
            return NORMAL_ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() > ITEM_SPINNER_THRESHOLD ? mItems.size() + 1 : mItems.size();
    }
}
