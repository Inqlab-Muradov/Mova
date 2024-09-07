package com.example.movaapp.ui.home

import com.example.movaapp.model.MovieResponse

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val movies: MovieResponse) : HomeUiState()
    data class Error(val message: String) : HomeUiState()

}