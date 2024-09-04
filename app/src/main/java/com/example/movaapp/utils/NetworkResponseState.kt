package com.example.movaapp.utils

sealed class NetworkResponseState<out T> {
    object Loading : NetworkResponseState<Nothing>()
    data class Success<out T>(val result: T?) : NetworkResponseState<T>()
    data class Error(val exception: Exception) : NetworkResponseState<Nothing>()
}