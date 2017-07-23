package com.hn.screen.items;

import com.hn.data.Item;
import com.hn.network.ApiClient;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by stevenpungdumri on 6/27/17.
 */

@Singleton
@Module
public class TopItemsProviderModule {
    private ApiClient mApiClient;
    private static final String TAG = TopItemsProviderModule.class.getName();
    private static final int STORIES_BUFFER = 1;

    private int mItemsPerPage;
    private List<Long> mIds;
    private int mIndex;
    private PublishSubject mItemsPubSub = PublishSubject.create();

    public TopItemsProviderModule(int itemsPerPage) {
        mItemsPerPage = itemsPerPage;
    }
    private boolean mFetchingItems = false;

    @Provides
    TopItemsProviderModule provideTopItemsProvider(ApiClient apiClient) {
        return new TopItemsProviderModule(apiClient, mItemsPerPage);
    }

    public TopItemsProviderModule(ApiClient apiClient, int itemsPerPage) {
        mApiClient = apiClient;
        mItemsPerPage = itemsPerPage;
    }

    public Observable<List<Item>> bind() {
        return mItemsPubSub;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public void fetchMoreItems() {
        if (mIds == null || mIds.isEmpty()) {
            fetchTopItemIds();
            return;
        }

        if (mFetchingItems || mIndex == mIds.size()) return;
        mFetchingItems = true;

        int toIndex = mIndex + mItemsPerPage;
        if (mIndex >= mIds.size()) {
            mIndex = Math.min(mIndex, mIds.size());
            toIndex = mIds.size();
        }

        mApiClient.getItems(mIds.subList(mIndex, toIndex))
            .buffer(STORIES_BUFFER)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<Item>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {}

                @Override
                public void onNext(@NonNull List<Item> items) {
                    mItemsPubSub.onNext(items);
                }

                @Override
                public void onComplete() {
                    if (mIds.size() > 0) {
                        mIndex += mItemsPerPage;
                    }

                    mFetchingItems = false;
                }

                @Override
                public void onError(@NonNull Throwable e) {}
            });
    }

    private void fetchTopItemIds() {
        if (mIds != null && !mIds.isEmpty()) return;

        mApiClient.getTopItemIds()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<Long>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {}

                @Override
                public void onNext(@NonNull List<Long> ids) {
                    mIds = ids;
                    fetchMoreItems();
                }

                @Override
                public void onError(@NonNull Throwable e) {}

                @Override
                public void onComplete() {}
            });
    }
}
