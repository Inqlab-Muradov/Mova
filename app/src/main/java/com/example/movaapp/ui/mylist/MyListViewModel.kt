package com.example.movaapp.ui.mylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movaapp.local.MyListDao
import com.example.movaapp.local.MyListItem
import com.example.movaapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyListViewModel @Inject constructor(
    val repository: MovieRepository
) : ViewModel() {

    private val _myListItemList = MutableLiveData<List<MyListItem>>()
    val myListItemList: LiveData<List<MyListItem>> get() = _myListItemList

    private val _filteredMyList = MutableLiveData<List<MyListItem>>()
    val filteredMyList: LiveData<List<MyListItem>> get() = _filteredMyList

    init {
        getAllMyListItem()
    }

    fun getAllMyListItem() {
        viewModelScope.launch {
            repository.getAllMyListItem().collectLatest {
                _myListItemList.value = it
            }
        }

    }

    fun filterMyList(mediaType: String) {
        viewModelScope.launch {
            repository.filterMyList(mediaType).collectLatest {
                _filteredMyList.value = it
            }
        }
    }
}