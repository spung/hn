package com.hn.data

/**
 * Created by stevenpungdumri on 5/8/17.
 */

data class Item(
    val id: Long,
    val deleted: Boolean,
    val type: HNItemType,
    val by: String,
    val time: Long,
    val text: String,
    val dead: Boolean,
    val parent: Long,
    val poll: Long,
    val kids: LongArray,
    val url: String,
    val score: Int,
    val title: String,
    val parts: LongArray,
    val descendants: Int
)