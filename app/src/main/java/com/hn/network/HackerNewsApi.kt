package com.hn.network

import com.hn.data.Item
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by stevenpungdumri on 5/8/17.
 */
interface HackerNewsApi {
    @GET("topstories.json")
    fun getTopItemIds(): Observable<List<Long>>

    @GET("item/{id}.json")
    fun getItem(@Path("id") itemId: Long): Observable<Item>
}