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

    private val _popularMovieState = MutableLiveData<HomeUiState>()
    val popularMovieState: LiveData<HomeUiState> get() = _popularMovieState

    private val _topRatedMovieState = MutableLiveData<HomeUiState>()
    val topRatedMovieState: LiveData<HomeUiState> get() = _topRatedMovieState

    private val _nowPlayingMovieState = MutableLiveData<HomeUiState>()
    val nowPlayingMovieState: LiveData<HomeUiState> get() = _nowPlayingMovieState

    fun getPopularMovies() {
        viewModelScope.launch {
            movieRepository.getPopularMovies().collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _popularMovieState.value = HomeUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _popularMovieState.value = HomeUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _popularMovieState.value = HomeUiState.Loading
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
                            _topRatedMovieState.value = HomeUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _topRatedMovieState.value = HomeUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _topRatedMovieState.value = HomeUiState.Loading
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
                            _nowPlayingMovieState.value = HomeUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _nowPlayingMovieState.value = HomeUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _nowPlayingMovieState.value = HomeUiState.Loading
                    }
                }
            }

        }
    }
}