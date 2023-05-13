package com.android.pagingwithflow.model

data class GetMovieListResponse(
    val page: Int,
    val results: List<MoiveResultList>,
    val total_pages: Int,
    val total_results: Int
)