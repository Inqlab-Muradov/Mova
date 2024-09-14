package com.example.movaapp.ui.explore

import android.util.Log
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
class ExploreViewModel @Inject constructor(
    private val repository: MovieRepository
):ViewModel() {

    private val _searchMovieState = MutableLiveData<ExploreUiState>()
    val searchMoviesState :LiveData<ExploreUiState> get() = _searchMovieState

    private val _trendingTvSeriesState = MutableLiveData<ExploreUiState>()
    val trendingTvSeriesState : LiveData<ExploreUiState> get() = _trendingTvSeriesState

    fun getSearchMovies(query:String){
        viewModelScope.launch {
             repository.getSearchMovies(query).collectLatest {
                 when(it){
                     is NetworkResponseState.Success->{
                         it.result?.let {
                             Log.e("searching movies",it.toString())
                             _searchMovieState.value = ExploreUiState.Success(it)
                         }
                     }
                     is NetworkResponseState.Error->{
                         _searchMovieState.value =ExploreUiState.Error(it.exception.toString())
                     }
                     is NetworkResponseState.Loading->{
                         _searchMovieState.value = ExploreUiState.Loading
                     }
                 }
             }
        }
    }

    fun getTrendingTv(){
        viewModelScope.launch {
            repository.getTrendingTv().collectLatest {
                when(it){
                    is NetworkResponseState.Success->{

                        it.result?.let {
                            _trendingTvSeriesState.value = ExploreUiState.Success(it)
                        }
                    }
                    is NetworkResponseState.Error->{
                        _trendingTvSeriesState.value = ExploreUiState.Error(it.exception.toString())
                    }
                    is NetworkResponseState.Loading->{
                        _trendingTvSeriesState.value = ExploreUiState.Loading
                    }
                }
            }
        }
    }

}