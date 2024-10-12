package com.example.movaapp.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movaapp.repository.AuthRepository
import com.example.movaapp.utils.NetworkResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sp:SharedPreferences
) : ViewModel() {

    private val _authResult = MutableLiveData<AuthUiState>()
    val authResult: LiveData<AuthUiState> get() = _authResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _authResult.value = AuthUiState.Success
                        val editor = sp.edit()
                        editor.putBoolean("isLogin",true)
                        editor.apply()
                    }

                    is NetworkResponseState.Error -> {
                        _authResult.value = AuthUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _authResult.value = AuthUiState.Loading
                    }
                }
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            authRepository.register(email, password).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        _authResult.value = AuthUiState.Success
                    }

                    is NetworkResponseState.Error -> {
                        _authResult.value = AuthUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _authResult.value = AuthUiState.Loading
                    }
                }

            }
        }
    }
}