package com.hn.network;

import com.hn.data.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import timber.log.Timber;

/**
 * Created by stevenpungdumri on 6/3/17.
 */

@Module
public class HnApiClientModule implements ApiClient {
    private HackerNewsApi mApi;
    private Map<Long, Item> mCache;

    @Provides
    @Singleton
    ApiClient provideHnApiClient() {
        return new HnApiClientModule();
    }

    public HnApiClientModule() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.d(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://hacker-news.firebaseio.com/v0/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build();
        mApi = retrofit.create(HackerNewsApi.class);
        mCache = new HashMap<>();
    }

    public Observable<List<Long>> getTopItemIds() {
        return mApi.getTopItemIds();
    }

    public Observable<Item> getItems(List<Long> ids) {
        return Observable.just(ids)
            .concatMapIterable(new Function<List<Long>, List<Long>>() { // break out list of ids into separate observables
                @Override
                public List<Long> apply(@NonNull List<Long> ids) throws Exception {
                    return ids;
                }
            })
            .concatMap(new Function<Long, Observable<Item>>() { // for each id, map to HNItem with API call
                @Override
                public Observable<Item> apply(@NonNull Long id) throws Exception {
                    Item item = mCache.get(id);
                    if (item != null) {
                        return Observable.just(mCache.get(id));
                    }

                    return mApi.getItem(id);
                }
            })
            .doOnEach(new Observer<Item>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {}

                @Override
                public void onNext(@NonNull Item item) {
                    mCache.put(item.getId(), item);
                }

                @Override
                public void onError(@NonNull Throwable e) {}

                @Override
                public void onComplete() {}
            })
            .filter(new Predicate<Item>() {
                @Override
                public boolean test(@NonNull Item item) throws Exception {
                    return !item.getDead() && !item.getDeleted();
                }
            });
    }

    @Override
    public void clearCache() {
        mCache.clear();
    }

    @Override
    public Observable<Item> getComments(final Item parentItem) {
        if (parentItem.getKids().isEmpty()) {
            return Observable.just(parentItem);
        } else {
            return Observable.merge(
                Observable.just(parentItem),
                Observable.fromArray(parentItem.getKids())
                    .flatMap(new Function<List<Long>, ObservableSource<Item>>() {
                        @Override
                        public ObservableSource<Item> apply(@NonNull List<Long> longs) throws Exception {
                            return getItems(longs);
                        }
                    })
                    .flatMap(new Function<Item, ObservableSource<Item>>() {
                        @Override
                        public ObservableSource<Item> apply(@NonNull Item item) throws Exception {
                            item.setIndentation(parentItem.getIndentation() + 1);
                            return getComments(item);
                        }
                    })
            );
        }
    }
}
