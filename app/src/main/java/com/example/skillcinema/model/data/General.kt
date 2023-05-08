package com.example.skillcinema.model.data

import com.example.skillcinema.model.data.apiFilms.ApiFilms
import com.example.skillcinema.model.data.apiNew.ApiNewMovies
import com.example.skillcinema.model.data.apiTop.ApiTop

data class General(
    val new: ApiNewMovies? = null,
    val top: ApiTop? = null,
    val films: ApiFilms? = null,
)
