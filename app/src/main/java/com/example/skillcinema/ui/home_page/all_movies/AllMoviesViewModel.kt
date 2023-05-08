package com.example.skillcinema.ui.home_page.all_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skillcinema.App
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.network.ServiceApi
import com.example.skillcinema.model.repository.State
import com.example.skillcinema.ui.Helper
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AllMoviesViewModel : ViewModel() {

    @Inject
    lateinit var serviceApi: ServiceApi

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableStateFlow<State?>(null)
    val state = _state.asStateFlow()

    private val _newMovies = MutableLiveData<ApiNewMovies>()
    val newMovies = _newMovies

    init {
        App.getAppComponent().inject(this)
    }

    fun getMovies(title: String) {
        when (title) {
            "Примьеры" -> getNewMovie()
        }
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