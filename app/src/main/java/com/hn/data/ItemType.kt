package com.hn.data

import com.squareup.moshi.Json

/**
 * Created by stevenpungdumri on 5/8/17.
 */
enum class HNItemType {
    @Json(name = "job") JOB,
    @Json(name = "story") story,
    @Json(name = "comment") COMMENT,
    @Json(name = "poll") POLL,
    @Json(name = "pollopt") POLLOPT
}