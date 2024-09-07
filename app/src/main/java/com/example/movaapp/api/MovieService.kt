package com.example.movaapp.api

import com.example.movaapp.model.MovieResponse
import com.example.movaapp.utils.api_key
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/day")
    suspend fun getPopularMovies(
        @Query("api_key") apikey: String = api_key,
        @Query("language") language: String = "en-US"
    ): retrofit2.Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apikey: String = api_key,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): retrofit2.Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apikey: String = api_key,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): retrofit2.Response<MovieResponse>

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") apikey: String = api_key,
        @Query("include_adult") adult: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("query") query: String
    ): Response<MovieResponse>

    @GET("trending/tv/day")
    suspend fun getTrendingTv(
        @Query("api_key") apiKey: String = api_key,
        @Query("language") language: String = "en-US"
    ): Response<MovieResponse>
}