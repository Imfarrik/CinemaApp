package com.example.skillcinema.model.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.data.apiFilms.ApiFilms
import com.example.skillcinema.model.data.apiImages.ApiImages
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiSimilars.ApiSimilars
import com.example.skillcinema.model.data.apiSingleMovie.ApiSingleMovie
import com.example.skillcinema.model.data.apiSingleStaff.ApiSingleStaff
import com.example.skillcinema.model.data.apiStaff.ApiStaff
import com.example.skillcinema.model.data.apiTop.ApiTop
import com.example.skillcinema.model.data.db.MoviesHolder
import com.example.skillcinema.model.network.ServiceApi
import com.example.skillcinema.room.AppDatabase
import com.example.skillcinema.ui.helpers.Helper
import com.example.skillcinema.ui.adapters.all_movies.ItemPagingSource
import com.example.skillcinema.ui.adapters.all_movies.PagingSource
import com.example.skillcinema.ui.adapters.gallery_adapter.ImagePagingSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import kotlin.random.Random

class Repository(
    private val serviceApi: ServiceApi,
    private val appDatabase: AppDatabase,
    private val sharedPreferencesManager: SharedPreferencesManager,
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

    private val _movie = MutableLiveData<ApiSingleMovie>()
    val movie = _movie

    private val _staff = MutableLiveData<ApiStaff>()
    val staff = _staff

    private val _images = MutableLiveData<ApiImages>()
    val images = _images

    private val _similar = MutableLiveData<ApiSimilars>()
    val similar = _similar

    private val _singleStaff = MutableLiveData<ApiSingleStaff>()
    val singleStaff = _singleStaff

    private val _lisSize = MutableLiveData<MutableList<String>>()
    val lisSize = _lisSize

    val popular = Pager(PagingConfig(pageSize = 1)) {
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

    val stillImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.STILL
        )
    }

    val shootingImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.SHOOTING
        )
    }

    val posterImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.POSTER
        )
    }

    val fanArtImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.FAN_ART
        )
    }

    val promoImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.PROMO
        )
    }

    val conceptImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.CONCEPT
        )
    }

    val wallpaperImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.WALLPAPER
        )
    }

    val coverImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.COVER
        )
    }

    val screenShortImages = Pager(PagingConfig(pageSize = 1)) {
        ImagePagingSource(
            serviceApi = serviceApi,
            sharedPreferencesManager = sharedPreferencesManager,
            imageType = Constants.SCREENSHOT
        )
    }


    fun getSingleStaff(id: Int) {
        compositeDisposable.add(
            serviceApi.getSingleStaff(id).subscribe({
                _singleStaff.value = it
            }, {})
        )
    }

    fun getSimilarMovies(id: Int) {
        compositeDisposable.add(
            serviceApi.getSimilar(id).subscribe({
                _similar.value = it
            }, {})
        )
    }

    suspend fun getImagesStill(id: Int, isAll: Boolean) {

        val response = serviceApi.getImages(id, Constants.STILL)

        if (response.isSuccessful) {
            _images.value = response.body()
        }

        if (isAll) {

            val response1 = serviceApi.getImages(id, Constants.SHOOTING)
            val response2 = serviceApi.getImages(id, Constants.POSTER)
            val response3 = serviceApi.getImages(id, Constants.FAN_ART)
            val response4 = serviceApi.getImages(id, Constants.PROMO)
            val response5 = serviceApi.getImages(id, Constants.CONCEPT)
            val response6 = serviceApi.getImages(id, Constants.WALLPAPER)
            val response7 = serviceApi.getImages(id, Constants.COVER)
            val response8 = serviceApi.getImages(id, Constants.SCREENSHOT)

            val list = listOf(
                response,
                response1,
                response2,
                response3,
                response4,
                response5,
                response6,
                response7,
                response8
            )

            val test = mutableListOf<String>()

            list.forEach {
                if (it.isSuccessful) {
                    test.add(it.body()?.total.toString())
                }
            }

            _lisSize.value = test
        }


    }

    fun getActors(id: Int) {
        compositeDisposable.add(serviceApi.getStaff(id).subscribe({
            _staff.value = it
        }, {}))
    }

    fun getSingleMovie(id: Int) {
        compositeDisposable.add(serviceApi.getSingleMovie(id).subscribe({
            _movie.value = it
        }, {}))
    }

    fun getImageUrl(id: Int, url: (String) -> Unit) {
        compositeDisposable.add(serviceApi.getSingleMovie(id).subscribe({
            url(it.posterUrlPreview)
        }, {}))
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