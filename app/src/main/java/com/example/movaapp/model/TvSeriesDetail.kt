package com.example.movaapp.model

data class TvSeriesDetail(
    val adult: Boolean?,
    val backdrop_path: String?,
    val episode_run_time: List<Any>?,
    val first_air_date: String?,
//    val genres: List<GenreX>?,
    val homepage: String?,
    val id: Int?,
    val in_production: Boolean?,
    val languages: List<String>?,
    val last_air_date: String?,
    val name: String?,
    val next_episode_to_air: Any?,
    val number_of_episodes: Int?,
    val number_of_seasons: Int?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val status: String?,
    val tagline: String?,
    val type: String?,
    val vote_average: Double?,
    val vote_count: Int?
)