package com.example.skillcinema.model.network

import com.example.skillcinema.domain.Constants
import com.example.skillcinema.model.data.apiFilms.ApiFilms
import com.example.skillcinema.model.data.apiFilter.ApiFilter
import com.example.skillcinema.model.data.apiImages.ApiImages
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiSeason.ApiSeason
import com.example.skillcinema.model.data.apiSimilars.ApiSimilars
import com.example.skillcinema.model.data.apiSingleMovie.ApiSingleMovie
import com.example.skillcinema.model.data.apiSingleStaff.ApiSingleStaff
import com.example.skillcinema.model.data.apiStaff.ApiStaff
import com.example.skillcinema.model.data.apiTop.ApiTop
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    fun getNewMovies(year: Int, month: String): Single<ApiNewMovies>

    fun getTop(type: String = Constants.TOP_100_POPULAR_FILMS, page: Int = 1): Single<ApiTop>

    fun getFilter(): Single<ApiFilter>

    fun getFilms(
        countries: Int,
        genres: Int,
        order: String = Constants.RATING,
        type: String = Constants.FILM,
    ): Single<ApiFilms>

    fun getSeries(
        order: String = Constants.RATING,
        type: String = Constants.TV_SERIES,
    ): Single<ApiFilms>

    suspend fun getTopPage(
        type: String = Constants.TOP_100_POPULAR_FILMS,
        page: Int = 1,
    ): Response<ApiTop>

    suspend fun getFilmsPage(
        countries: Int,
        genres: Int,
        order: String = Constants.RATING,
        type: String = Constants.FILM,
        page: Int = 1,
    ): Response<ApiFilms>

    suspend fun getSeriesPage(
        order: String = Constants.RATING,
        type: String = Constants.TV_SERIES,
        page: Int = 1,
    ): Response<ApiFilms>

    fun getSingleMovie(id: Int): Single<ApiSingleMovie>

    fun getStaff(id: Int): Single<ApiStaff>

    suspend fun getImages(id: Int, type: String, page: Int = 1): Response<ApiImages>

    fun getSimilar(id: Int): Single<ApiSimilars>

    fun getSingleStaff(id: Int): Single<ApiSingleStaff>

    fun getSingleSeason(id: Int): Single<ApiSeason>

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
    ): Observable<ApiFilms>

    fun searchKeyWord(keyword: String): Observable<ApiFilms>


}