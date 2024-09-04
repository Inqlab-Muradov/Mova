package com.example.movaapp.repository

import com.example.movaapp.api.MovieService
import com.example.movaapp.utils.NetworkResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val apiService: MovieService
) {

    private fun<T> safeApiRequest(apiCall:suspend()-> Response<T>) : Flow<NetworkResponseState<T>> = flow {
        emit(NetworkResponseState.Loading)
        val response = apiCall.invoke()
        if (response.isSuccessful){
            response.body()?.let {
                emit(NetworkResponseState.Success(it))
            }?: emit(NetworkResponseState.Error(Exception("Error")))
        }else{
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

}