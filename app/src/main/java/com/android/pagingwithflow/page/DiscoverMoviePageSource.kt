package com.android.pagingwithflow.page

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.pagingwithflow.model.MoiveResultList
import com.android.pagingwithflow.network.ApiService
import com.android.pagingwithflow.network.NetworkingConstants.API_KEY
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException


class DiscoverMoviePageSource
constructor(private val apiService: ApiService) : PagingSource<Int, MoiveResultList>() {

    private val DEFAULT_PAGE_INDEX = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoiveResultList> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.getDiscoverMovie(API_KEY, page)
            Log.e("TAG_DiscoverMoviePageSource", ": " + response.results)
            // simulate page loading
            if (page != 0) delay(5000)
            LoadResult.Page(
                response.results,
                prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoiveResultList>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}