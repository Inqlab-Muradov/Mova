package com.example.movaapp.model

data class ReviewResponse(
    val id: Int?,
    val page: Int?,
    val results: List<ResultReviews>?,
    val total_pages: Int?,
    val total_results: Int?
)