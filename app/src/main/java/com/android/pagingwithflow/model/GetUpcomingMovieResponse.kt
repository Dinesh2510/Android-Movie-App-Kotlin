package com.android.pagingwithflow.model


import com.google.gson.annotations.SerializedName


data class GetUpcomingMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<UpcomingResultList>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)