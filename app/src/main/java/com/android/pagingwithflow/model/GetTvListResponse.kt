package com.android.pagingwithflow.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTvListResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<TVResultList>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)