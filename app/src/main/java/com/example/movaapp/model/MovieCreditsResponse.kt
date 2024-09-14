package com.example.movaapp.model

data class MovieCreditsResponse(
    val cast: List<Cast>?,
    val crew: List<Crew>?,
    val id: Int?
)