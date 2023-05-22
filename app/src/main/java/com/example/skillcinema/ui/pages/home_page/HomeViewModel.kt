package com.example.skillcinema.ui.pages.home_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillcinema.App
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.domain.SharedPreferencesManager
import com.example.skillcinema.model.data.db.MoviesHolder
import com.example.skillcinema.model.repository.Repository
import com.example.skillcinema.model.repository.State
import com.example.skillcinema.room.AppDatabase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var appDatabase: AppDatabase

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess = _isSuccess

    private val _onError = MutableLiveData(false)
    val onError = _onError


    init {
        App.getAppComponent().inject(this)
//        sharedPreferencesManager.saveAuthToken(Constants.API_KEY)
        sharedPreferencesManager.saveAuthToken(Constants.API_KEY_RESERVE)
        repository.getMovies()
        getState()
    }


    private fun getState() {
        viewModelScope.launch {
            repository.state.collect {
                when (it) {
                    State.Loading -> {
                        _isLoading.value = true
                        _isSuccess.value = false
                    }
                    is State.Error -> {
                        _isLoading.value = true
                        _isSuccess.value = false
                        _onError.value = true
                    }
                    State.Success -> {
                        _isLoading.value = false
                        _isSuccess.value = true
                    }
                    null -> {}
                }
            }
        }

    }


    fun insert(moviesHolder: MoviesHolder) = viewModelScope.launch {
        repository.insertMovieType(moviesHolder)
    }

    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }


}