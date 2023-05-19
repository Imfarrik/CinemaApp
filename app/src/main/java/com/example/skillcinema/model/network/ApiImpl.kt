package com.example.skillcinema.model.network

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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class ApiImpl(private val api: Api) : ServiceApi {

    override fun getNewMovies(year: Int, month: String): Single<ApiNewMovies> {
        return api.getNewMovies(year, month).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTop(type: String, page: Int): Single<ApiTop> {
        return api.getTop(type = type, page = page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFilter(): Single<ApiFilter> {
        return api.getFilter().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFilms(
        countries: Int,
        genres: Int,
        order: String,
        type: String,
    ): Single<ApiFilms> {
        return api.getFilms(countries, genres, order, type).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSeries(order: String, type: String): Single<ApiFilms> {
        return api.getSeries(order, type).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun getTopPage(type: String, page: Int): Response<ApiTop> {
        return api.getTopPage(type, page)
    }

    override suspend fun getFilmsPage(
        countries: Int,
        genres: Int,
        order: String,
        type: String,
        page: Int,
    ): Response<ApiFilms> {
        return api.getFilmsPage(countries, genres, order, type, page)
    }

    override suspend fun getSeriesPage(order: String, type: String, page: Int): Response<ApiFilms> {
        return api.getSeriesPage(order, type, page)
    }

    override fun getSingleMovie(id: Int): Single<ApiSingleMovie> {
        return api.getSingleMovie(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getStaff(id: Int): Single<ApiStaff> {
        return api.getStaff(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun getImages(id: Int, type: String, page: Int): Response<ApiImages> {
        return api.getImages(id, type, page)
    }

    override fun getSimilar(id: Int): Single<ApiSimilars> {
        return api.getSimilar(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSingleStaff(id: Int): Single<ApiSingleStaff> {
        return api.getSingleStaff(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSingleSeason(id: Int): Single<ApiSeason> {
        return api.getSingleSeason(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun search(
        order: String?,
        countries: Int?,
        genres: Int?,
        type: String?,
        page: Int?,
        ratingFrom: Int?,
        ratingTo: Int?,
        yearFrom: Int?,
        yearTo: Int?,
        imdbId: Int?,
        keyword: String?,
    ): Observable<ApiFilms> {
        return api.search(
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
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}