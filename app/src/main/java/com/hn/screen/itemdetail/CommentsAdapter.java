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

public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private List<Item> mItems;
    private boolean mComplete;

    public CommentsAdapter(ItemDetailViewModel itemDetailViewModel, ArrayList restoredItems,
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
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CommentViewHolder(layoutInflater.inflate(R.layout.layout_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Item item = mItems.get(position + 1);
        holder.bindView(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size() - 1;
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
