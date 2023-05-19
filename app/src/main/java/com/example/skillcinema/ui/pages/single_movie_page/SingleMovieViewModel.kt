package com.example.skillcinema.ui.pages.single_movie_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.App
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.data.db.*
import com.example.skillcinema.model.repository.Repository
import com.example.skillcinema.room.AppDatabase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SingleMovieViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var appDatabase: AppDatabase

    private val _allWatched = MutableLiveData<List<WatchedHolder>>()
    val allWatched = _allWatched

    private val _allSaved = MutableLiveData<List<SaveHolder>>()
    val allSaved = _allSaved

    private val _allLiked = MutableLiveData<List<LikeHolder>>()
    val allLiked = _allLiked

    private val _allCollections = MutableLiveData<List<CollectionHolder>>()
    val allCollections = _allCollections

    init {
        App.getAppComponent().inject(this)
        getDBData()
    }

    val movie = repository.movie
    val staff = repository.staff
    val images = repository.images
    val similar = repository.similar
    val season = repository.season

    fun getDBData() {
        getWatchedMovie()
        getSavedMovie()
        getLikedMovie()
        getAllCollections()
    }

    fun insertInterestedMovie(wasInterestedHolder: WasInterestedHolder) {
        viewModelScope.launch {
            appDatabase.collectionDao().insertInterestedMovie(wasInterestedHolder)
        }
    }

    fun insertCollection(name: String) {
        viewModelScope.launch {
            appDatabase.collectionDao().insertCollection(CollectionHolder(name))
        }
    }

    private fun getAllCollections() {
        viewModelScope.launch {
            _allCollections.value = appDatabase.collectionDao().getAllCollections()
        }
    }

    fun insertLikedMovie(likeHolder: LikeHolder) {
        viewModelScope.launch {
            appDatabase.collectionDao().insertLikedMovie(likeHolder)
        }
    }

    private fun getLikedMovie() {
        viewModelScope.launch {
            _allLiked.value = appDatabase.collectionDao().getAllLike()
        }
    }

    fun deleteLikedMovie(movieId: Int) {
        viewModelScope.launch {
            appDatabase.collectionDao().deleteLikedMovie(movieId)
        }
    }

    fun insertWatchedMovie(watchedHolder: WatchedHolder) {
        viewModelScope.launch {
            appDatabase.collectionDao().insertWatchedMovie(watchedHolder)
        }
    }

    private fun getWatchedMovie() {
        viewModelScope.launch {
            _allWatched.value = appDatabase.collectionDao().getAllWatched()
        }
    }

    fun deleteWatchedMovie(movieId: Int) {
        viewModelScope.launch {
            appDatabase.collectionDao().deleteWatchedMovie(movieId)
        }
    }

    fun saveMovie(saveHolder: SaveHolder) {
        viewModelScope.launch {
            appDatabase.collectionDao().insertSavedMovie(saveHolder)
        }
    }

    fun unSaveMovie(movieId: Int) {
        viewModelScope.launch {
            appDatabase.collectionDao().deleteSavedMovie(movieId)
        }
    }

    private fun getSavedMovie() {
        viewModelScope.launch {
            _allSaved.value = appDatabase.collectionDao().getAllSavedMovie()
        }
    }


    fun getSeason(id: Int) {
        repository.getSeason(id)
    }

    fun getStaff(id: Int) {
        repository.getActors(id)
        repository.getSingleMovie(id)
        repository.getSimilarMovies(id)
        viewModelScope.launch {
            repository.getImagesStill(id, false)
        }
    }

    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }

}