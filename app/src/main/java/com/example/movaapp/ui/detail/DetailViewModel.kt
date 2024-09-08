package com.example.movaapp.ui.detail

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
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
)  : ViewModel(){

    private val _moviesDetailState = MutableLiveData<DetailUiState>()
    val moviesDetailState :LiveData<DetailUiState> get() = _moviesDetailState

    private val _tvSeriesDetailState = MutableLiveData<DetailUiState>()
    val tvSeriesDetailState :LiveData<DetailUiState> get() = _tvSeriesDetailState

    fun getMoviesDetail(id:Int){
        viewModelScope.launch {
            repository.getMoviesDetail(id).collectLatest {
                when(it){
                    is NetworkResponseState.Success->{
                        it.result?.let {
                            _moviesDetailState.value = DetailUiState.Success(it)
                        }
                    }
                    is NetworkResponseState.Error->{
                        _moviesDetailState.value = DetailUiState.Error(it.exception.toString())
                    }
                    is NetworkResponseState.Loading->{
                        _moviesDetailState.value = DetailUiState.Loading
                    }
                }
            }
        }
    }

    fun getTvSeriesDetail(id:Int){
        viewModelScope.launch {
            repository.getTvSeriesDetail(id).collectLatest {
                when(it){
                    is NetworkResponseState.Success->{
                        it.result?.let {
                            _tvSeriesDetailState.value= DetailUiState.Success(it)
                        }
                    }
                    is NetworkResponseState.Error->{
                        _tvSeriesDetailState.value = DetailUiState.Error(it.exception.toString())
                    }
                    is NetworkResponseState.Loading->{
                        _tvSeriesDetailState.value = DetailUiState.Loading
                    }
                }
            }
        }
    }
}