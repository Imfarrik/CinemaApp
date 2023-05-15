package com.example.skillcinema.ui.pages.seasons_page

import androidx.lifecycle.ViewModel
import com.example.skillcinema.App
import com.example.skillcinema.model.repository.Repository
import javax.inject.Inject

class SeasonsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    init {
        App.getAppComponent().inject(this)
    }

}