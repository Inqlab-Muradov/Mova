package com.example.movaapp.ui.explore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movaapp.repository.MovieRepository
import com.example.movaapp.ui.home.UiState
import com.example.movaapp.utils.NetworkResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: MovieRepository
):ViewModel() {

    private val _trendingTvSeriesState = MutableLiveData<UiState>()
    val trendingTvSeries :LiveData<UiState> get() = _trendingTvSeriesState

    private val _searchMovieState = MutableLiveData<UiState>()
    val searchMovieState:LiveData<UiState> get() = _searchMovieState



    fun getSearchMovies(query:String){
        viewModelScope.launch {
             repository.getSearchMovies(query).collectLatest {
                 when(it){
                     is NetworkResponseState.Success->{
                         it.result?.let {
                             Log.e("searching movies",it.toString())
                             _searchMovieState.value = UiState.Success(it)
                         }
                     }
                     is NetworkResponseState.Error->{
                         _searchMovieState.value =UiState.Error(it.exception.toString())
                     }
                     is NetworkResponseState.Loading->{
                         _searchMovieState.value = UiState.Loading
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
                            _trendingTvSeriesState.value = UiState.Success(it)
                        }
                    }
                    is NetworkResponseState.Error->{
                        _trendingTvSeriesState.value = UiState.Error(it.exception.toString())
                    }
                    is NetworkResponseState.Loading->{
                        _trendingTvSeriesState.value = UiState.Loading
                    }
                }
            }
        }
    }

}