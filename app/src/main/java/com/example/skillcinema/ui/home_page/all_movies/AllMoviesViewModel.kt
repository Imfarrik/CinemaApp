package com.example.skillcinema.ui.home_page.all_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.App
import com.example.skillcinema.model.data.General
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiTop.ApiTop
import com.example.skillcinema.model.network.ServiceApi
import com.example.skillcinema.model.repository.State
import com.example.skillcinema.room.AppDatabase
import com.example.skillcinema.ui.Helper
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllMoviesViewModel : ViewModel() {

    @Inject
    lateinit var serviceApi: ServiceApi

    @Inject
    lateinit var appDatabase: AppDatabase

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    private val _newMovies = MutableLiveData<ApiNewMovies>()
    val newMovies = _newMovies

    private val _title = MutableLiveData<String>()
    val title = _title

    private val _topMovies = MutableLiveData<ApiTop>()
    val topMovies = _topMovies

    init {
        App.getAppComponent().inject(this)
    }

    private val funcList = listOf(getNewMovie(), getTopMovies())

    fun getAllTypes(id: String) {
        viewModelScope.launch {

            val moviesHolder = appDatabase.typeDao().getAll()

            for (i in moviesHolder.indices) {

                if (moviesHolder[i].id == id.toInt()) {
                    _title.value = moviesHolder[i].type
                }

            }

            moviesHolder.forEach {


                if (it.id == id.toInt()) {
                    _title.value = it.type
                    getNewMovie()
                }

            }
        }

    }

    private fun getTopMovies() {
        compositeDisposable.add(
            serviceApi.getTop().subscribe({
                _topMovies.value = it
            }, {})
        )
    }

    private fun getNewMovie() {

        _state.value = State.Loading

        compositeDisposable.add(
            serviceApi.getNewMovies(year = Helper.getYear(), month = Helper.getMonth()).subscribe({
                _state.value = State.Success
                _newMovies.value = it
            }, {
                _state.value = State.ServerError(it.message ?: "Error 5xx")
            })
        )

    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}