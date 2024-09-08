package com.example.movaapp.ui.detail

import com.example.movaapp.model.MoviesDetailResponse

sealed class DetailUiState {

    data class Success(val moviesDetailResponse: MoviesDetailResponse) : DetailUiState()
    data class Error (val message:String) : DetailUiState()
    object Loading:DetailUiState()
}