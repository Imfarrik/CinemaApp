package com.example.skillcinema.model.network

import com.example.skillcinema.domain.Constants.FILM
import com.example.skillcinema.domain.Constants.RATING
import com.example.skillcinema.domain.Constants.TOP_100_POPULAR_FILMS
import com.example.skillcinema.domain.Constants.TV_SERIES
import com.example.skillcinema.model.data.apiFilms.ApiFilms
import com.example.skillcinema.model.data.apiFilter.ApiFilter
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiSingleMovie.ApiSingleMovie
import com.example.skillcinema.model.data.apiTop.ApiTop
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("/api/v2.2/films/premieres")
    fun getNewMovies(
        @Query("year") year: Int,
        @Query("month") month: String,
    ): Single<ApiNewMovies>


    @GET("/api/v2.2/films/top")
    fun getTop(
        @Query("type") type: String = TOP_100_POPULAR_FILMS,
        @Query("page") page: Int = 1,
    ): Single<ApiTop>

    @GET("/api/v2.2/films/filters")
    fun getFilter(): Single<ApiFilter>

    @GET("/api/v2.2/films")
    fun getFilms(
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("order") order: String = RATING,
        @Query("type") type: String = FILM,
    ): Single<ApiFilms>

    @GET("/api/v2.2/films")
    fun getSeries(
        @Query("order") order: String = RATING,
        @Query("type") type: String = TV_SERIES,
        @Query("page") page: Int = 1
    ): Single<ApiFilms>

    @GET("/api/v2.2/films/{id}")
    fun getSingleMovie(
        @Path("id") id: Int = 301
    ): Single<ApiSingleMovie>

    @GET("/api/v2.2/films/top")
    suspend fun getTopPage(
        @Query("type") type: String = TOP_100_POPULAR_FILMS,
        @Query("page") page: Int = 1,
    ): Response<ApiTop>

    @GET("/api/v2.2/films")
    suspend fun getFilmsPage(
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("order") order: String = RATING,
        @Query("type") type: String = FILM,
        @Query("page") page: Int = 1
    ): Response<ApiFilms>

    @GET("/api/v2.2/films")
    suspend fun getSeriesPage(
        @Query("order") order: String = RATING,
        @Query("type") type: String = TV_SERIES,
        @Query("page") page: Int = 1
    ): Response<ApiFilms>

}