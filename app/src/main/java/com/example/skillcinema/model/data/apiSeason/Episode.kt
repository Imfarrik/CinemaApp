package com.example.skillcinema.model.data.apiSeason

data class Episode(
    val episodeNumber: Int,
    val nameEn: String?,
    val nameRu: String?,
    val releaseDate: String?,
    val seasonNumber: Int,
    val synopsis: String,
)