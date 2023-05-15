package com.example.skillcinema.ui.pages.search_page

import androidx.lifecycle.ViewModel
import com.example.skillcinema.App
import com.example.skillcinema.model.repository.Repository
import javax.inject.Inject

class SearchViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    init {
        App.getAppComponent().inject(this)
    }

    val result = repository.search

    fun searchByKeyWord(value: String) {
        repository.searchKeyWord(value)
    }


    fun search(
        order: String,
        countries: Int,
        genres: Int,
        type: String,
        page: Int,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        imdbId: Int,
        keyword: String,
    ) {
        repository.search(
            order,
            countries,
            genres,
            type,
            page,
            ratingFrom,
            ratingTo,
            yearFrom,
            yearTo,
            imdbId,
            keyword
        )
    }
}