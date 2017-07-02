package com.hn.network;

import com.hn.data.Item;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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

    @Provides
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
    }

    public Observable<List<Long>> getTopItemIds() {
        return mApi.getTopItemIds();
    }

    public Single<List<Item>> getItems(List<Long> ids) {
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
                    return mApi.getItem(id);
                }
            })
            .toList();
    }
}
