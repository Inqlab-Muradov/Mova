package com.example.movaapp.ui.home

import com.example.movaapp.model.MovieResponse

sealed class UiState {
    object Loading : UiState()
    data class Success(val movies: MovieResponse) : UiState()
    data class Error(val message: String) : UiState()

}