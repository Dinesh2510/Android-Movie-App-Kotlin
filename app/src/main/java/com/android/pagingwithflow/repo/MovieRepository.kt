package com.android.pagingwithflow.repo

import com.android.pagingwithflow.model.GetGenresResponse
import com.android.pagingwithflow.model.GetMovieDetailsResponse
import com.android.pagingwithflow.model.GetTrailerResponse
import com.android.pagingwithflow.model.GetUpcomingMovieResponse
import com.android.pagingwithflow.network.ApiService
import com.android.pagingwithflow.network.NetworkingConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    fun getUpcomingMovie(): Flow<GetUpcomingMovieResponse> = flow {
        val response = apiService.getUpcomingMovie(NetworkingConstants.API_KEY, "1")
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getPopularMovie(): Flow<GetUpcomingMovieResponse> = flow {
        val response = apiService.getPopularMovie(NetworkingConstants.API_KEY, "1")
        emit(response)
    }.flowOn(Dispatchers.IO)


    fun getTopRatedMovie(): Flow<GetUpcomingMovieResponse> = flow {
        val response = apiService.getTopRatedMovie(NetworkingConstants.API_KEY, "1")
        emit(response)
    }.flowOn(Dispatchers.IO)


    fun getGenreMovie(): Flow<GetGenresResponse> = flow {
        val response = apiService.getGenreMovie(NetworkingConstants.API_KEY)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getMovieDetails(movie_id: String): Flow<GetMovieDetailsResponse> = flow {
        val response = apiService.getMovieDetails(movie_id,NetworkingConstants.API_KEY)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getTrailerResponse(movie_id: String): Flow<GetTrailerResponse> = flow {
        val response = apiService.getTrailerResponse(movie_id,NetworkingConstants.API_KEY)
        emit(response)
    }.flowOn(Dispatchers.IO)
}