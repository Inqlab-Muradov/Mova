package com.example.movaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movaapp.local.MyListItem
import com.example.movaapp.repository.MovieRepository
import com.example.movaapp.ui.detail.DetailViewModel.MoviesVideoUiState
import com.example.movaapp.utils.NetworkResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _moviesVideoResponse = MutableLiveData<MoviesVideoUiState>()
    val moviesVideoResponse:LiveData<MoviesVideoUiState> get() = _moviesVideoResponse

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

    fun addMyList(myListItem: MyListItem){
       viewModelScope.launch (Dispatchers.IO){
           movieRepository.addMyList(myListItem)
       }
    }

    fun getMovieVideo(id:Int){
        viewModelScope.launch {
            movieRepository.getMoviesVideo(id).collectLatest {
                when(it){
                    is NetworkResponseState.Success->{
                        it.result?.let {result->
                            _moviesVideoResponse.value = MoviesVideoUiState.Success(result)
                        }
                    }
                    is NetworkResponseState.Error->{
                        _moviesVideoResponse.value = MoviesVideoUiState.Error(it.exception.toString())
                    }
                    is NetworkResponseState.Loading->{
                        _moviesVideoResponse.value = MoviesVideoUiState.Loading
                    }
                }
            }
        }
    }
}