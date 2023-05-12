package com.example.skillcinema.ui.pages.single_movie_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.App
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SingleMovieViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager


    init {
        App.getAppComponent().inject(this)
    }

    val movie = repository.movie
    val staff = repository.staff
    val images = repository.images
    val similar = repository.similar

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