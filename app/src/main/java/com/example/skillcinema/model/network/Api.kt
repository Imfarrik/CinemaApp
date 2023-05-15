package com.example.skillcinema.model.network

import com.example.skillcinema.domain.Constants.FILM
import com.example.skillcinema.domain.Constants.RATING
import com.example.skillcinema.domain.Constants.TOP_100_POPULAR_FILMS
import com.example.skillcinema.domain.Constants.TV_SERIES
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
        @Query("page") page: Int = 1,
    ): Single<ApiFilms>

    @GET("/api/v1/staff")
    fun getStaff(
        @Query("filmId") id: Int,
    ): Single<ApiStaff>

    @GET("/api/v2.2/films/{id}")
    fun getSingleMovie(
        @Path("id") id: Int,
    ): Single<ApiSingleMovie>

    @GET("/api/v2.2/films/{id}/similars")
    fun getSimilar(
        @Path("id") id: Int,
    ): Single<ApiSimilars>

    @GET("/api/v1/staff/{id}")
    fun getSingleStaff(
        @Path("id") id: Int,
    ): Single<ApiSingleStaff>

    @GET("/api/v2.2/films/{id}/seasons")
    fun getSingleSeason(
        @Path("id") id: Int,
    ): Single<ApiSeason>

    @GET("/api/v2.2/films/{id}/images")
    suspend fun getImages(
        @Path("id") id: Int,
        @Query("type") type: String,
        @Query("page") page: Int = 1,
    ): Response<ApiImages>

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
        @Query("page") page: Int = 1,
    ): Response<ApiFilms>

    @GET("/api/v2.2/films")
    suspend fun getSeriesPage(
        @Query("order") order: String = RATING,
        @Query("type") type: String = TV_SERIES,
        @Query("page") page: Int = 1,
    ): Response<ApiFilms>

    @GET("/api/v2.2/films")
    fun search(
        @Query("order") order: String,
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("imdbId") imdbId: Int,
        @Query("keyword") keyword: String,
    ): Observable<ApiFilms>

    @GET("/api/v2.2/films")
    fun searchKeyWord(
        @Query("keyword") keyword: String,
    ): Observable<ApiFilms>

}