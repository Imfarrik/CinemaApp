package com.example.skillcinema.ui.pages.actor_page

import androidx.lifecycle.ViewModel
import com.example.skillcinema.App
import com.example.skillcinema.model.repository.Repository
import javax.inject.Inject

class ActorViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    init {
        App.getAppComponent().inject(this)
    }

    val singleStaff = repository.singleStaff

    fun getSingleStaff(id: Int) {
        repository.getSingleStaff(id)
    }

    override fun onCleared() {
        repository.onCleared()
        super.onCleared()
    }
}