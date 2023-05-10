package com.example.skillcinema.ui.all_movies_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.skillcinema.App
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.network.ServiceApi
import com.example.skillcinema.model.repository.Repository
import com.example.skillcinema.ui.adapters.all_movies.ItemPagingSource
import com.example.skillcinema.ui.adapters.all_movies.PagingSource
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllMoviesViewModel : ViewModel() {

    @Inject
    lateinit var serviceApi: ServiceApi

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    init {
        App.getAppComponent().inject(this)
    }

    val state = repository.state

    val popular = repository.popular.flow.cachedIn(viewModelScope)
    val top250 = repository.top250.flow.cachedIn(viewModelScope)
    val random = repository.random.flow.cachedIn(viewModelScope)
    val serials = repository.serials.flow.cachedIn(viewModelScope)

    fun getAllTypes(id: String) = viewModelScope.launch {
        repository.getAllTypes(id)
    }


    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }
}