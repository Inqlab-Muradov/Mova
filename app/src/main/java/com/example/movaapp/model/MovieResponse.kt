package com.example.movaapp.model

data class MovieResponse(
    val page: Int?,
    val results: List<Result>?,
    val totalPages: Int?,
    val totalResults: Int?
)