package com.example.skillcinema.ui.pages.filter_page

import android.text.Editable
import android.text.TextWatcher
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import com.example.skillcinema.App
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.repository.Repository
import com.example.skillcinema.room.AppDatabase
import java.util.*
import javax.inject.Inject

class FilterViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    init {
        App.getAppComponent().inject(this)
        repository.getAllCountryAndGenres()
    }

    val countryList = repository.allCountry
    val genreList = repository.allGenres

    var type = ""
    var country = ""
    var genre = ""
    var year = ""
    var order = ""
    var rating: List<Float> = listOf()


    fun getType() {

        type = sharedPreferencesManager.getType() ?: ""
        country =
            if (sharedPreferencesManager.getCountry() != null) sharedPreferencesManager.getCountry()
                .toString().split(",")[1] else "Выберите страну"

        genre =
            if (sharedPreferencesManager.getGenre() != null) sharedPreferencesManager.getGenre()
                .toString().split(",")[1] else "Выберите жанр"

        year =
            if (sharedPreferencesManager.getYearFrom() != null)
                " С ${sharedPreferencesManager.getYearFrom()} до ${sharedPreferencesManager.getYearTo()}"
            else "Выберите год"

        rating = listOf(
            (sharedPreferencesManager.getRatingFrom() ?: "1").toFloat(),
            (sharedPreferencesManager.getRatingTo() ?: "10").toFloat()
        )

        order = sharedPreferencesManager.getOrder() ?: ""

    }

    fun saveOrder(id: String?) {
        sharedPreferencesManager.saveOrder(id)
    }

    fun saveRateFrom(id: String) {
        sharedPreferencesManager.saveRatingFrom(id)
    }

    fun saveRateTo(id: String) {
        sharedPreferencesManager.saveRatingTo(id)
    }

    fun searchByKeyCountry(key: String) {
        repository.searchByKeyCountry(key)
    }


    fun searchByKeyGenre(key: String) {
        repository.searchByKeyGenre(key)
    }

    fun saveType(id: String?) {
        sharedPreferencesManager.saveType(id)
    }

    fun saveGenre(id: String) {
        sharedPreferencesManager.saveGenre(id)
    }

    fun saveCountry(id: String) {
        sharedPreferencesManager.saveCountry(id)
    }

    fun savePeriodFrom(year: Int) {
        sharedPreferencesManager.saveYearFrom(year.toString())
    }

    fun savePeriodTo(year: Int) {
        sharedPreferencesManager.saveYearTo(year.toString())
    }

    val countryTxtWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int,
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val query = s.toString().trim()

            searchByKeyCountry(query)

        }
    }

    val genreTxtWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int,
        ) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val query = s.toString().trim()

            searchByKeyGenre(query)

        }
    }


}