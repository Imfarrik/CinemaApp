package com.example.skillcinema.ui.pages.profile_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.App
import com.example.skillcinema.model.data.db.CollectionHolder
import com.example.skillcinema.model.data.db.WasInterestedHolder
import com.example.skillcinema.model.data.db.WatchedHolder
import com.example.skillcinema.room.AppDatabase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private val _allWatched = MutableLiveData<List<WatchedHolder>>()
    val allWatched = _allWatched

    private val _allCollection = MutableLiveData<List<CollectionHolder>>()
    val allCollection = _allCollection

    private val _allInterested = MutableLiveData<List<WasInterestedHolder>>()
    val allInterested = _allInterested

    init {
        App.getAppComponent().inject(this)
        getAll()
    }

    fun getAll() {
        getAllWatched()
        getAllInterested()
        getAllCollections()
    }

    fun insertNewCollection(name: String) {
        viewModelScope.launch {
            appDatabase.collectionDao().insertCollection(CollectionHolder(name))
            getAllCollections()
        }
    }

    private fun getAllWatched() {
        viewModelScope.launch {
            _allWatched.value = appDatabase.collectionDao().getAllWatched()
        }
    }

    private fun getAllCollections() {
        viewModelScope.launch {
            _allCollection.value = appDatabase.collectionDao().getAllCollections()
        }
    }

    private fun getAllInterested() {
        viewModelScope.launch {
            _allInterested.value = appDatabase.collectionDao().getAllInterested()
        }
    }


}