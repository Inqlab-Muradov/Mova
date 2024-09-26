package com.example.movaapp.repository

import com.example.movaapp.api.MovieService
import com.example.movaapp.local.MyListDao
import com.example.movaapp.local.MyListItem
import com.example.movaapp.utils.NetworkResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val apiService: MovieService,
    val myListDao: MyListDao
) {

    private fun <T> safeApiRequest(apiCall: suspend () -> Response<T>): Flow<NetworkResponseState<T>> =
        flow {
            emit(NetworkResponseState.Loading)
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(NetworkResponseState.Success(it))
                } ?: emit(NetworkResponseState.Error(Exception("Error")))
            } else {
                emit(NetworkResponseState.Error(Exception(response.errorBody().toString())))
            }
        }.catch {
            emit(NetworkResponseState.Error(it as Exception))
        }.flowOn(
            Dispatchers.IO
        )

    fun getPopularMovies() = safeApiRequest {
        apiService.getPopularMovies()
    }

    fun getTopRatedMovies() = safeApiRequest {
        apiService.getTopRatedMovies()
    }

    fun getNowPlayingMovies() = safeApiRequest {
        apiService.getNowPlayingMovies()
    }

    fun getSearchMovies(query: String) = safeApiRequest {
        apiService.getSearchMovies(query = query)
    }

    fun getTrendingTv() = safeApiRequest {
        apiService.getTrendingTv()
    }

    fun getMoviesDetail(id: Int) = safeApiRequest {
        apiService.getMoviesDetail(id)
    }

    fun getTvSeriesDetail(id: Int) = safeApiRequest {
        apiService.getTvSeriesDetail(id)
    }

    fun getMovieCredits(id: Int) = safeApiRequest {
        apiService.getMoviesCredits(id)
    }

    fun getTvSeriesName(id: Int) = safeApiRequest {
        apiService.getTvSeriesName(id)
    }

    fun getTvSeriesCredits(id: Int) = safeApiRequest {
        apiService.getTvSeriesCredits(id)
    }

    fun getMovieRecommendations(id: Int) = safeApiRequest {
        apiService.getMoviesRecommendations(id)
    }

    fun getTvSeriesRecommend(id: Int) = safeApiRequest {
        apiService.getTvSeriesRecommend(id)
    }

    fun getMoviesReviews(id: Int) = safeApiRequest {
        apiService.getMoviesReviews(id)
    }

    fun getTvSeriesReviews(id: Int) = safeApiRequest {
        apiService.getTvSeriesReviews(id)
    }

    fun addMyList(myListItem: MyListItem) {
        myListDao.addMyListItem(myListItem)
    }

    fun getAllMyListItem() = myListDao.getAllMyListItem().flowOn(Dispatchers.IO)

    fun filterMyList(mediaType: String) = myListDao.filterMyList(mediaType).flowOn(Dispatchers.IO)

    fun deleteMyListItem(myListItem: MyListItem) {
        myListDao.deleteMyListItem(myListItem)
    }

    fun getMyListById(id: Int) = myListDao.getMyListById(id).flowOn(Dispatchers.IO)


}