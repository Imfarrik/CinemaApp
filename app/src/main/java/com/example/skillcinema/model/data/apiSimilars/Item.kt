package com.example.skillcinema.model.data.apiSimilars

data class Item(
    val filmId: Int,
    val nameEn: String?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val relationType: String
)