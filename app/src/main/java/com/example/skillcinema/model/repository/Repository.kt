package com.example.skillcinema.model.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.data.apiFilms.ApiFilms
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiTop.ApiTop
import com.example.skillcinema.model.data.db.MoviesHolder
import com.example.skillcinema.model.network.ServiceApi
import com.example.skillcinema.room.AppDatabase
import com.example.skillcinema.ui.Helper
import com.example.skillcinema.ui.adapters.all_movies.ItemPagingSource
import com.example.skillcinema.ui.adapters.all_movies.PagingSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import kotlin.random.Random

class Repository(
    private val serviceApi: ServiceApi,
    private val appDatabase: AppDatabase,
    private val sharedPreferencesManager: SharedPreferencesManager
) {

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    private val _newMovies = MutableLiveData<ApiNewMovies>()
    val newMovies = _newMovies

    private val _topMovies = MutableLiveData<ApiTop>()
    val topMovies = _topMovies

    private val _randomMovies = MutableLiveData<ApiFilms>()
    val randomMovies = _randomMovies

    private val _randomName = MutableLiveData<String>()
    val randomName = _randomName

    private val _top250Movies = MutableLiveData<ApiTop>()
    val top250Movies = _top250Movies

    private val _topSeries = MutableLiveData<ApiFilms>()
    val topSeries = _topSeries

    private val _title = MutableLiveData<String>()
    val title = _title

    val popular = Pager(PagingConfig(pageSize = 1)) {
        _state.value
        PagingSource(serviceApi, false)
    }

    val top250 = Pager(PagingConfig(pageSize = 1)) {
        PagingSource(serviceApi, true)
    }

    val random = Pager(PagingConfig(pageSize = 1)) {
        ItemPagingSource(serviceApi, sharedPreferencesManager, false)
    }

    val serials = Pager(PagingConfig(pageSize = 1)) {
        ItemPagingSource(serviceApi, sharedPreferencesManager, true)
    }


    fun getMovies() {

        _state.value = State.Loading

        getNewMovie()

        getTopMovies()

        getRandomMovie()

        getTop250()

        getSeries()

    }

    suspend fun getAllTypes(id: String) {

        _state.value = State.Loading

        val moviesHolder = appDatabase.typeDao().getAll()

        for (i in moviesHolder.indices) {

            if (moviesHolder[i].id == id.toInt()) {
                _title.value = moviesHolder[i].type
                _state.value = State.Success
            }
        }

        when (id.toInt()) {
            0 -> getNewMovie()
//            1 -> getTopMovies()
//            2 -> getRandomMovie()
//            3 -> getTop250()
//            4 -> getSeries()
        }

    }

    private fun getNewMovie() {
        compositeDisposable.add(
            serviceApi.getNewMovies(year = Helper.getYear(), month = Helper.getMonth()).subscribe({
                _state.value = State.Success
                _newMovies.value = it
            }, {
                _state.value = State.ServerError(it.message ?: "Error 5xx")
            })
        )
    }

    suspend fun insertMovieType(moviesHolder: MoviesHolder) {

        appDatabase.typeDao().insert(moviesHolder)

    }

    private fun getTopMovies() {
        compositeDisposable.add(
            serviceApi.getTop().subscribe({
                _state.value = State.Success
                _topMovies.value = it
            }, {})
        )
    }

    private fun getSeries() {
        compositeDisposable.add(
            serviceApi.getSeries().subscribe({
                _state.value = State.Success
                _topSeries.value = it
            }, {})
        )
    }

    private fun getRandomMovie() {
        compositeDisposable.add(
            serviceApi.getFilter().subscribe({

                val randomGenre = Random.nextInt(from = 0, until = it.genres.size)
                val randomCountry = Random.nextInt(from = 0, until = it.countries.size)

                compositeDisposable.add(
                    serviceApi.getFilms(
                        countries = it.countries[randomCountry].id,
                        genres = it.genres[randomGenre].id
                    ).subscribe({ randomFilms ->

                        if (randomFilms.items.isNotEmpty()) {

                            sharedPreferencesManager.saveCountryId(it.countries[randomCountry].id)
                            sharedPreferencesManager.saveGenreId(it.genres[randomGenre].id)

                            _randomName.value =
                                "${
                                    it.genres[randomGenre].genre.replaceFirstChar { latter ->
                                        if (latter.isLowerCase()) latter.titlecase(
                                            Locale.ROOT
                                        ) else latter.toString()
                                    }
                                } ${it.countries[randomCountry].country}"

                            _randomMovies.value = randomFilms

                        } else {
                            sharedPreferencesManager.saveCountryId(1)
                            sharedPreferencesManager.saveGenreId(1)
                            compositeDisposable.add(
                                serviceApi.getFilms(
                                    1,
                                    1
                                ).subscribe({ randomFilms2 ->

                                    _randomName.value =
                                        "${
                                            it.genres[0].genre.replaceFirstChar { latter ->
                                                if (latter.isLowerCase()) latter.titlecase(
                                                    Locale.ROOT
                                                ) else latter.toString()
                                            }
                                        } ${it.countries[0].country}"

                                    _randomMovies.value = randomFilms2

                                }, {})
                            )
                        }
                    }, {})
                )

            }, {})
        )
    }

    private fun getTop250() {
        compositeDisposable.add(
            serviceApi.getTop(type = Constants.TOP_250_BEST_FILMS).subscribe({
                _state.value = State.Success
                _top250Movies.value = it
            }, {})
        )
    }

    fun onCleared() {
        compositeDisposable.clear()
    }


}