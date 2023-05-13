package com.android.pagingwithflow.page

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.pagingwithflow.network.ApiService
import retrofit2.HttpException
import java.io.IOException


class MarsPageSource/*
constructor(private val apiService: ApiService) : PagingSource<Int, MarsModel>() {

    private val DEFAULT_PAGE_INDEX= 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarsModel> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.getAllPhotos(page,params.loadSize)
            LoadResult.Page(
                response,
                prevKey = if(page == DEFAULT_PAGE_INDEX) null else page-1,
                nextKey = if(response.isEmpty()) null else page+1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MarsModel>): Int? {
        return null
    }
}*/