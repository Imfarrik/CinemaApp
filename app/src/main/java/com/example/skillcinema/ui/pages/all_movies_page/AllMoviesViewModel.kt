package com.example.skillcinema.ui.pages.all_movies_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.skillcinema.App
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.data.db.*
import com.example.skillcinema.model.network.ServiceApi
import com.example.skillcinema.model.repository.Repository
import com.example.skillcinema.room.AppDatabase
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
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val _allWatched = MutableLiveData<List<WatchedHolder>>()
    val allWatched = _allWatched

    private val _allInterested = MutableLiveData<List<WasInterestedHolder>>()
    val allInterested = _allInterested

    private val _allMoviesInCollection = MutableLiveData<List<MoviesInCollection>>()
    val allMoviesInCollection = _allMoviesInCollection

    private val _allSaved = MutableLiveData<List<SaveHolder>>()
    val allSaved = _allSaved

    private val _allLiked = MutableLiveData<List<LikeHolder>>()
    val allLiked = _allLiked

    init {
        App.getAppComponent().inject(this)
    }

    val state = repository.state

    val popular = repository.popular.flow.cachedIn(viewModelScope)
    val top250 = repository.top250.flow.cachedIn(viewModelScope)
    val random = repository.random.flow.cachedIn(viewModelScope)
    val serials = repository.serials.flow.cachedIn(viewModelScope)

    fun getAllMoviesInCollection(type: String) {
        viewModelScope.launch {
            _allMoviesInCollection.value = appDatabase.collectionDao().getMovieInCollection(type)
        }
    }

    fun getLiked() {
        viewModelScope.launch {
            launch {
                _allLiked.value = appDatabase.collectionDao().getAllLike()
            }
        }
    }

    fun getSaved() {
        viewModelScope.launch {
            launch {
                _allSaved.value = appDatabase.collectionDao().getAllSavedMovie()
            }
        }
    }

    fun getWatched() {
        viewModelScope.launch {
            launch {
                _allWatched.value = appDatabase.collectionDao().getAllWatched()
            }
        }
    }

    fun getInterested() {
        viewModelScope.launch {
            launch {
                _allInterested.value = appDatabase.collectionDao().getAllInterested()
            }
        }
    }

    fun clearInterested() {
        viewModelScope.launch {
            appDatabase.collectionDao().deleteAllInterestedMovie()
        }
    }

    fun getAllTypes(id: String) = viewModelScope.launch {
        repository.getAllTypes(id)
    }


    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }
}