package com.example.skillcinema.model.network

import com.example.skillcinema.model.data.apiFilms.ApiFilms
import com.example.skillcinema.model.data.apiFilter.ApiFilter
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiTop.ApiTop
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ApiImpl(private val api: Api) : ServiceApi {

    override fun getNewMovies(year: Int, month: String): Single<ApiNewMovies> {
        return api.getNewMovies(year, month).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTop(type: String): Single<ApiTop> {
        return api.getTop(type = type).subscribeOn(Schedulers.io())
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
        type: String
    ): Single<ApiFilms> {
        return api.getFilms(countries, genres, order, type).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSeries(order: String, type: String): Single<ApiFilms> {
        return api.getSeries(order, type).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}