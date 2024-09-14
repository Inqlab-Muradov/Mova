package com.example.movaapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movaapp.model.MovieCreditsResponse
import com.example.movaapp.model.ReviewResponse
import com.example.movaapp.model.TvSeriesDetail
import com.example.movaapp.repository.MovieRepository
import com.example.movaapp.ui.home.HomeUiState
import com.example.movaapp.utils.NetworkResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _moviesDetailState = MutableLiveData<DetailUiState>()
    val moviesDetailState: LiveData<DetailUiState> get() = _moviesDetailState

    private val _tvSeriesDetailState = MutableLiveData<DetailUiState>()
    val tvSeriesDetailState: LiveData<DetailUiState> get() = _tvSeriesDetailState

    private val _movieCreditsResponse = MutableLiveData<MovieCreditsUiState>()
    val movieCreditsResponse: LiveData<MovieCreditsUiState> get() = _movieCreditsResponse

    private val _tvSeriesNameResponse = MutableLiveData<TvSeriesNameState>()
    val tvSeriesNameResponse: LiveData<TvSeriesNameState> get() = _tvSeriesNameResponse

    private val _tvSeriesCreditsResponse = MutableLiveData<MovieCreditsUiState>()
    val tvSeriesCreditsResponse: LiveData<MovieCreditsUiState> get() = _tvSeriesCreditsResponse

    private val _movieRecommendResponse = MutableLiveData<HomeUiState>()
    val movieRecommend: LiveData<HomeUiState> get() = _movieRecommendResponse

    private val _tvSeriesRecommendResponse = MutableLiveData<HomeUiState>()
    val tvSeriesRecommendResponse: LiveData<HomeUiState> get() = _tvSeriesRecommendResponse

    private val _movieReviewsResponse = MutableLiveData<MovieReviewsUiState>()
    val movieReviewResponse:LiveData<MovieReviewsUiState> get() = _movieReviewsResponse

    private val _tvSeriesReviewsResponse = MutableLiveData<MovieReviewsUiState>()
    val tvSeriesReviewsResponse:LiveData<MovieReviewsUiState> get() = _tvSeriesReviewsResponse



    fun getMoviesDetail(id: Int) {
        viewModelScope.launch {
            repository.getMoviesDetail(id).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _moviesDetailState.value = DetailUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _moviesDetailState.value = DetailUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _moviesDetailState.value = DetailUiState.Loading
                    }
                }
            }
        }
    }

    fun getTvSeriesDetail(id: Int) {
        viewModelScope.launch {
            repository.getTvSeriesDetail(id).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _tvSeriesDetailState.value = DetailUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _tvSeriesDetailState.value = DetailUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _tvSeriesDetailState.value = DetailUiState.Loading
                    }
                }
            }
        }
    }

    fun getMovieCredits(id: Int) {
        viewModelScope.launch {
            repository.getMovieCredits(id).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _movieCreditsResponse.value = MovieCreditsUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _movieCreditsResponse.value =
                            MovieCreditsUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _movieCreditsResponse.value = MovieCreditsUiState.Loading
                    }
                }
            }
        }

    }

    fun getTvSeriesName(id: Int) {
        viewModelScope.launch {
            repository.getTvSeriesName(id).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _tvSeriesNameResponse.value = TvSeriesNameState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _tvSeriesNameResponse.value =
                            TvSeriesNameState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _tvSeriesNameResponse.value = TvSeriesNameState.Loading
                    }
                }
            }
        }
    }

    fun getTvSeriesCredits(id: Int) {
        viewModelScope.launch {
            repository.getTvSeriesCredits(id).collectLatest {
                when (it) {
                    is NetworkResponseState.Success -> {
                        it.result?.let {
                            _tvSeriesCreditsResponse.value = MovieCreditsUiState.Success(it)
                        }
                    }

                    is NetworkResponseState.Error -> {
                        _tvSeriesCreditsResponse.value =
                            MovieCreditsUiState.Error(it.exception.toString())
                    }

                    is NetworkResponseState.Loading -> {
                        _tvSeriesCreditsResponse.value = MovieCreditsUiState.Loading
                    }
                }
            }
        }
    }

    fun getMovieRecommendations(id: Int) {
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

    fun getMovieReviews(id:Int){
        viewModelScope.launch {
            repository.getMoviesReviews(id).collectLatest {
                when(it){
                    is NetworkResponseState.Success->{
                        it.result?.let {
                            _movieReviewsResponse.value = MovieReviewsUiState.Success(it)
                        }
                    }
                    is NetworkResponseState.Error->{
                        _movieReviewsResponse.value = MovieReviewsUiState.Error(it.exception.toString())
                    }
                    is NetworkResponseState.Loading->{
                        _movieReviewsResponse.value = MovieReviewsUiState.Loading
                    }
                }
            }
        }
    }

    fun getTvSeriesReviews(id:Int){
        viewModelScope.launch {
            repository.getTvSeriesReviews(id).collectLatest {
                when(it){
                    is NetworkResponseState.Success->{
                        it.result?.let {
                            _tvSeriesReviewsResponse.value = MovieReviewsUiState.Success(it)
                        }
                    }
                    is NetworkResponseState.Error->{
                        _tvSeriesReviewsResponse.value = MovieReviewsUiState.Error(it.exception.toString())
                    }
                    is NetworkResponseState.Loading->{
                        _tvSeriesReviewsResponse.value = MovieReviewsUiState.Loading
                    }
                }
            }
        }
    }

    sealed class MovieCreditsUiState {
        data class Success(val response: MovieCreditsResponse) : MovieCreditsUiState()
        data class Error(val message: String) : MovieCreditsUiState()
        object Loading : MovieCreditsUiState()
    }

    sealed class TvSeriesNameState {
        data class Success(val response: TvSeriesDetail) : TvSeriesNameState()
        data class Error(val message: String) : TvSeriesNameState()
        object Loading : TvSeriesNameState()
    }

    sealed class MovieReviewsUiState{
        data class Success(val response:ReviewResponse):MovieReviewsUiState()
        data class Error(val message:String):MovieReviewsUiState()
        object Loading:MovieReviewsUiState()
    }
}