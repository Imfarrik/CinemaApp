package com.example.skillcinema.ui.pages.search_page

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModel
import com.example.skillcinema.App
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.repository.Repository
import javax.inject.Inject

class SearchViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    init {
        App.getAppComponent().inject(this)
    }

    val result = repository.search
    val isEmptyList = repository.isEmptyList
    val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val query: String? = s?.toString()?.trim()

            search(keyword = query)

        }
    }

//    fun update() {
//        _type = sharedPreferencesManager.getType()
//        _country =
//            if (sharedPreferencesManager.getCountry() != null) sharedPreferencesManager.getCountry()
//                .toString().split(",")[0].toInt() else null
//        _genre =
//            if (sharedPreferencesManager.getGenre() != null) sharedPreferencesManager.getGenre()
//                .toString().split(",")[0].toInt() else null
//        _order = sharedPreferencesManager.getOrder()
//
//        _ratingFrom =
//            if (sharedPreferencesManager.getRatingFrom() != null) sharedPreferencesManager.getRatingFrom()!!
//                .toFloat().toInt() else null
//
//        _ratingTo =
//            if (sharedPreferencesManager.getRatingTo() != null) sharedPreferencesManager.getRatingTo()!!
//                .toFloat().toInt() else null
//
//        _yearFrom =
//            if (sharedPreferencesManager.getYearFrom() != null) sharedPreferencesManager.getYearFrom()!!
//                .toInt() else null
//
//        _yearTo =
//            if (sharedPreferencesManager.getYearTo() != null) sharedPreferencesManager.getYearTo()!!
//                .toInt() else null
//    }


    fun search(
        keyword: String? = null,
    ) {
        val type: String? = sharedPreferencesManager.getType()
        val country: Int? =
            if (sharedPreferencesManager.getCountry() != null) sharedPreferencesManager.getCountry()
                .toString().split(",")[0].toInt() else null
        val genre: Int? =
            if (sharedPreferencesManager.getGenre() != null) sharedPreferencesManager.getGenre()
                .toString().split(",")[0].toInt() else null
        val order: String? = sharedPreferencesManager.getOrder()

        val ratingFrom: Int? =
            if (sharedPreferencesManager.getRatingFrom() != null) sharedPreferencesManager.getRatingFrom()!!
                .toFloat().toInt() else null

        val ratingTo: Int? =
            if (sharedPreferencesManager.getRatingTo() != null) sharedPreferencesManager.getRatingTo()!!
                .toFloat().toInt() else null

        val yearFrom: Int? =
            if (sharedPreferencesManager.getYearFrom() != null) sharedPreferencesManager.getYearFrom()!!
                .toInt() else null

        val yearTo: Int? =
            if (sharedPreferencesManager.getYearTo() != null) sharedPreferencesManager.getYearTo()!!
                .toInt() else null

        repository.search(
            order = order,
            countries = country,
            genres = genre,
            type = type,
            page = 1,
            ratingFrom = ratingFrom,
            ratingTo = ratingTo,
            yearFrom = yearFrom,
            yearTo = yearTo,
            imdbId = null,
            keyword = keyword
        )
    }
}