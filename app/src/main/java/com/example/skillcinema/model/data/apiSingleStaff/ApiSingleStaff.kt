package com.example.skillcinema.model.data.apiSingleStaff

data class ApiSingleStaff(
    val age: Int,
    val birthday: String,
    val birthplace: String,
    val death: Any,
    val deathplace: Any,
    val facts: List<Any>,
    val films: List<Film>,
    val growth: Int,
    val hasAwards: Int,
    val nameEn: String?,
    val nameRu: String?,
    val personId: Int,
    val posterUrl: String,
    val profession: String,
    val sex: String,
    val spouses: List<Any>,
    val webUrl: String
)