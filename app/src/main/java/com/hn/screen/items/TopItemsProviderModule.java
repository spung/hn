package com.hn.screen.items;

import com.hn.data.Item;
import com.hn.network.ApiClient;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
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

    private int mItemsPerPage;
    private List<Long> mIds;
    private PublishSubject mItemsPubSub = PublishSubject.create();

    public TopItemsProviderModule(int itemsPerPage) {
        mItemsPerPage = itemsPerPage;
    }

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

    public void fetchMoreItems() {
        if (mIds == null || mIds.isEmpty()) {
            fetchTopItemIds();
            return;
        }

        mApiClient.getItems(mIds.subList(0, mItemsPerPage))
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<Item>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {}

                @Override
                public void onSuccess(@NonNull List<Item> items) {
                    mItemsPubSub.onNext(items);
                    mIds = mIds.subList(mItemsPerPage, mIds.size());
                }

                @Override
                public void onError(@NonNull Throwable e) {}
            });
    }

    private void fetchTopItemIds() {
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
