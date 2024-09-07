package com.example.movaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movaapp.repository.MovieRepository
import com.example.movaapp.utils.NetworkResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _popularMovieState = MutableLiveData<UiState>()
    val popularMovieState: LiveData<UiState> get() = _popularMovieState

    private val _topRatedMovieState = MutableLiveData<UiState>()
    val topRatedMovieState: LiveData<UiState> get() = _topRatedMovieState

    private val _nowPlayingMovieState = MutableLiveData<UiState>()
    val nowPlayingMovieState: LiveData<UiState> get() = _nowPlayingMovieState

    fun getPopularMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies().collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _popularMovieState.value = UiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _popularMovieState.value = UiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _popularMovieState.value = UiState.Loading
                    }
                }
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            movieRepository.getTopRatedMovies().collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _topRatedMovieState.value = UiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _topRatedMovieState.value = UiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _topRatedMovieState.value = UiState.Loading
                    }
                }
            }
        }
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            movieRepository.getNowPlayingMovies().collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _nowPlayingMovieState.value = UiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _nowPlayingMovieState.value = UiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _nowPlayingMovieState.value = UiState.Loading
                    }
                }
            }

        }
    }
}