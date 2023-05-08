package com.example.skillcinema.model.data.apiFilms

data class ApiFilms(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)