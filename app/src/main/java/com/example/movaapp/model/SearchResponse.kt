package com.example.movaapp.model

data class SearchResponse(
    val page: Int?,
    val results: List<ResultX>?,
    val total_pages: Int?,
    val total_results: Int?
)