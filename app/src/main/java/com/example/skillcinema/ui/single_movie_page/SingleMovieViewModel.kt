package com.example.skillcinema.ui.single_movie_page

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skillcinema.App
import com.example.skillcinema.model.data.apiSingleMovie.ApiSingleMovie
import com.example.skillcinema.model.network.ServiceApi
import javax.inject.Inject

class SingleMovieViewModel : ViewModel() {

    @Inject
    lateinit var serviceApi: ServiceApi

    private val _movie = MutableLiveData<ApiSingleMovie>()
    val movie = _movie

    init {
        App.getAppComponent().inject(this)
    }

    @SuppressLint("CheckResult")
    fun getSingleMovie(id: Int) {
        serviceApi.getSingleMovie(id).subscribe({
            _movie.value = it
        }, {})
    }
}