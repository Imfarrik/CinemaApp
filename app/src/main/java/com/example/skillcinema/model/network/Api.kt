package com.example.skillcinema.model.network

import com.example.skillcinema.domain.Constants.FILM
import com.example.skillcinema.domain.Constants.RATING
import com.example.skillcinema.domain.Constants.TOP_100_POPULAR_FILMS
import com.example.skillcinema.domain.Constants.TV_SERIES
import com.example.skillcinema.model.data.apiFilms.ApiFilms
import com.example.skillcinema.model.data.apiFilter.ApiFilter
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiTop.ApiTop
import io.reactivex.rxjava3.core.Single
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
    ): Single<ApiFilms>

}