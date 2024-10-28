package com.example.movaapp.model

data class MoviesDetailResponse(
    val adult: Boolean?,
    val backdrop_path: String?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_title: String?,
    val production_countries: List<ProductionCountry>?,
    val production_companies: List<ProductionCompany>?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: String,
    val vote_count: Int?
)