package com.android.pagingwithflow.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pagingwithflow.model.GenreResultList
import com.android.pagingwithflow.model.GetMovieDetailsResponse
import com.android.pagingwithflow.model.TrailerResultList
import com.android.pagingwithflow.model.UpcomingResultList
import com.android.pagingwithflow.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val apiService: ApiService,
    private val postRepository: MovieRepository
) : ViewModel() {
    val upcomingLiveData: MutableLiveData<List<UpcomingResultList>> = MutableLiveData()
    val popularLiveData: MutableLiveData<List<UpcomingResultList>> = MutableLiveData()
    val topRatedLiveData: MutableLiveData<List<UpcomingResultList>> = MutableLiveData()
    val genreLiveData: MutableLiveData<List<GenreResultList>> = MutableLiveData()
    val getTrailerLiveData: MutableLiveData<List<TrailerResultList>> = MutableLiveData()
    val movieDetailLivedata: MutableLiveData<GetMovieDetailsResponse> = MutableLiveData()

    /*
        fun UpcomingMovieResult(): Flow<GetUpcomingMovieList> {
            return flow {

                // get the comment Data from the api
                val comment = apiService.getUpcomingMovie(NetworkingConstants.API_KEY)

                // Emit this data wrapped in
                // the helper class [CommentApiState]
                emit(comment)
            }.flowOn(Dispatchers.IO)
        }
    */

    fun getUpcomingMovieResult() {
        viewModelScope.launch {
            postRepository.getUpcomingMovie()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    upcomingLiveData.value = response.results
                }

        }
    }

    fun getPopularMovieResult() {
        viewModelScope.launch {
            postRepository.getPopularMovie()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    popularLiveData.value = response.results
                }

        }
    }

    fun getTopRatedMovieResult() {
        viewModelScope.launch {
            postRepository.getTopRatedMovie()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    topRatedLiveData.value = response.results
                }

        }
    }

    fun getGenreMovieResult() {
        viewModelScope.launch {
            postRepository.getGenreMovie()
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    genreLiveData.value = response.genres
                }

        }
    }

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            postRepository.getMovieDetails(movieId)
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    movieDetailLivedata.value = response
                }

        }
    }
    fun getTrailerResponse(movieId: String) {
        viewModelScope.launch {
            postRepository.getTrailerResponse(movieId)
                .catch { e ->
                    Log.d("main", "getPost: ${e.message}")
                }.collect { response ->
                    getTrailerLiveData.value = response.results
                }

        }
    }

}