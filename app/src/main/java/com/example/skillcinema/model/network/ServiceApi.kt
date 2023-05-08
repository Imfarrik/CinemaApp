package com.example.skillcinema.model.network

import com.example.skillcinema.domain.Constants
import com.example.skillcinema.model.data.apiFilms.ApiFilms
import com.example.skillcinema.model.data.apiFilter.ApiFilter
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiTop.ApiTop
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    fun getNewMovies(year: Int, month: String): Single<ApiNewMovies>

    fun getTop(type: String = Constants.TOP_100_POPULAR_FILMS): Single<ApiTop>

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

}