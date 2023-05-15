package com.example.skillcinema.ui.pages.filmography_page

import androidx.lifecycle.ViewModel
import com.example.skillcinema.App
import com.example.skillcinema.domain.Constants
import com.example.skillcinema.model.data.apiSingleStaff.Film
import com.example.skillcinema.model.repository.Repository
import javax.inject.Inject

class FilmographyViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    init {
        App.getAppComponent().inject(this)
    }

//    val writer = mutableListOf<Film>()
//    val director = mutableListOf<Film>()
//    val producer = mutableListOf<Film>()
//    val himself = mutableListOf<Film>()
//    val actor = mutableListOf<Film>()

//    fun getAllMoviesSize(movieList: List<Film>) {
//        movieList.forEach {
//            when (it.professionKey) {
//                Constants.WRITER -> writer.add(it)
//                Constants.DIRECTOR -> director.add(it)
//                Constants.PRODUCER -> producer.add(it)
//                Constants.HIMSELF -> himself.add(it)
//                Constants.ACTOR -> actor.add(it)
//            }
//        }
//    }
}