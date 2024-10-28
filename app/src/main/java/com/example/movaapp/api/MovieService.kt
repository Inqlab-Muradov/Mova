package com.example.movaapp.api

import com.example.movaapp.model.MovieCreditsResponse
import com.example.movaapp.model.MovieResponse
import com.example.movaapp.model.MoviesDetailResponse
import com.example.movaapp.model.MoviesVideoResponse
import com.example.movaapp.model.ResultX
import com.example.movaapp.model.ReviewResponse
import com.example.movaapp.model.SearchResponse
import com.example.movaapp.model.TvSeriesDetail
import com.example.movaapp.utils.api_key
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/day")
    suspend fun getPopularMovies( ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<MovieResponse>

    @GET("search/multi")
    suspend fun getSearchMovies(@Query("query") query: String): Response<SearchResponse>

    @GET("trending/tv/day")
    suspend fun getTrendingTv(): Response<SearchResponse>

    @GET("movie/{id}")
    suspend fun getMoviesDetail(@Path("id") id: Int ): Response<MoviesDetailResponse>

    @GET("tv/{id}")
    suspend fun getTvSeriesDetail(@Path("id") id: Int ): Response<MoviesDetailResponse>

    @GET("tv/{id}")
    suspend fun getTvSeriesName(@Path("id") id: Int): Response<TvSeriesDetail>

    @GET("movie/{id}/credits")
    suspend fun getMoviesCredits(@Path("id") id: Int): Response<MovieCreditsResponse>

    @GET("tv/{id}/credits")
    suspend fun getTvSeriesCredits(@Path("id") id: Int): Response<MovieCreditsResponse>

    @GET("movie/{id}/recommendations")
    suspend fun getMoviesRecommendations( @Path("id") id: Int): Response<MovieResponse>

    @GET("tv/{id}/recommendations")
    suspend fun getTvSeriesRecommend(@Path("id") id: Int): Response<MovieResponse>

    @GET("movie/{id}/reviews")
    suspend fun getMoviesReviews(@Path("id") id: Int): Response<ReviewResponse>

    @GET("tv/{id}/reviews")
    suspend fun getTvSeriesReviews( @Path("id") id: Int): Response<ReviewResponse>

    @GET("movie/{id}/videos")
    suspend fun getMoviesVideos( @Path("id") id: Int): Response<MoviesVideoResponse>

    @GET("tv/{id}/videos")
    suspend fun getTvSeriesVideos(@Path("id") id: Int):Response<MoviesVideoResponse>
}