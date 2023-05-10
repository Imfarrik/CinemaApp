package com.example.skillcinema.ui.home_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.App
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.data.db.MoviesHolder
import com.example.skillcinema.model.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var repository: Repository

    init {
        App.getAppComponent().inject(this)
        sharedPreferencesManager.saveAuthToken(Constants.API_KEY)
        repository.getMovies()
    }

    fun insert(moviesHolder: MoviesHolder) = viewModelScope.launch {
        repository.insertMovieType(moviesHolder)
    }

    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }


}