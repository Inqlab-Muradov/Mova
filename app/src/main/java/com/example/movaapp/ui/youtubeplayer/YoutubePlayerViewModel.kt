package com.example.movaapp.ui.youtubeplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movaapp.repository.MovieRepository
import com.example.movaapp.ui.detail.DetailViewModel.MoviesVideoUiState
import com.example.movaapp.ui.home.HomeUiState
import com.example.movaapp.utils.NetworkResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class YoutubePlayerViewModel @Inject constructor(
    private var repository:MovieRepository
): ViewModel() {

    private val _movieRecommendResponse = MutableLiveData<HomeUiState>()
    val movieRecommend: LiveData<HomeUiState> get() = _movieRecommendResponse

    private val _tvSeriesRecommendResponse = MutableLiveData<HomeUiState>()
    val tvSeriesRecommendResponse: LiveData<HomeUiState> get() = _tvSeriesRecommendResponse

    private val _moviesVideoResponse = MutableLiveData<MoviesVideoUiState>()
    val moviesVideoResponse:LiveData<MoviesVideoUiState> get() = _moviesVideoResponse

    private val _tvSeriesVideoResponse = MutableLiveData<MoviesVideoUiState>()
    val tvSeriesVideoResponse:LiveData<MoviesVideoUiState> get() = _tvSeriesVideoResponse

    fun getMovieRecommendations(id:Int){
        viewModelScope.launch {
            repository.getMovieRecommendations(id).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _movieRecommendResponse.value = HomeUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _movieRecommendResponse.value = HomeUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _movieRecommendResponse.value = HomeUiState.Loading
                    }
                }
            }
        }
    }

    fun getTvSeriesRecommend(id:Int){
        viewModelScope.launch {
            repository.getTvSeriesRecommend(id).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _tvSeriesRecommendResponse.value = HomeUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _tvSeriesRecommendResponse.value = HomeUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _tvSeriesRecommendResponse.value = HomeUiState.Loading
                    }
                }
            }
        }
    }

    fun getMoviesVideo(id:Int){
        viewModelScope.launch {
            repository.getMoviesVideo(id).collectLatest {
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

    fun getTvSeriesVideo(id:Int){
        viewModelScope.launch {
            repository.getTvSeriesVideos(id).collectLatest {
                when(it){
                    is NetworkResponseState.Success->{
                        it.result?.let {result->
                            _tvSeriesVideoResponse.value = MoviesVideoUiState.Success(result)
                        }
                    }
                    is NetworkResponseState.Error->{
                        _tvSeriesVideoResponse.value = MoviesVideoUiState.Error(it.exception.toString())
                    }
                    is NetworkResponseState.Loading->{
                        _tvSeriesVideoResponse.value = MoviesVideoUiState.Loading
                    }
                }
            }
        }
    }

}