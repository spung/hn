package com.hn.screen.itemdetail;

import android.os.Bundle;
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
 * Created by stevenpungdumri on 7/5/17.
 */

public class ItemDetailAdapter extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_ITEM_DETAIL = 0;
    public static final int VIEW_TYPE_COMMENT = 1;

    private List<Item> mItems;
    private boolean mComplete;

    public ItemDetailAdapter(ItemDetailViewModel itemDetailViewModel, ArrayList restoredItems,
                             final FirstItemListener firstItemListener ) {
        if (restoredItems == null) {
            mItems = new ArrayList<>();
            itemDetailViewModel.bind().subscribe(new Observer<Item>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {}

                @Override
                public void onNext(@NonNull Item comment) {
                    if (mItems.size() == 1) {
                        firstItemListener.onFirstItemFetched();
                    }

                    mItems.add(comment);
                    notifyItemInserted(mItems.size());
                }

                @Override
                public void onError(@NonNull Throwable e) {}

                @Override
                public void onComplete() {
                    mComplete = true;
                }
            });
        } else {
            mItems = restoredItems;
            mComplete = true;
        }
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

    public void onSaveInstanceState(Bundle bundle, String key) {
        if (mComplete) {
            bundle.putParcelableArrayList(key, (ArrayList) mItems);
        }
    }

    public interface FirstItemListener {
        void onFirstItemFetched();
    }
}
