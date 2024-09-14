package com.example.movaapp.ui.explore

import com.example.movaapp.model.ResultX
import com.example.movaapp.model.SearchResponse

sealed class ExploreUiState {
    data class Success(val result:SearchResponse):ExploreUiState()
    data class Error(val message:String):ExploreUiState()
    object Loading:ExploreUiState()
}