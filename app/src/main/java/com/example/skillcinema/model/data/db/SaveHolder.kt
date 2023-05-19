package com.example.skillcinema.model.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save")
data class SaveHolder(
    @PrimaryKey
    var movie_id: Int,
    var name_ru: String?,
    var name_en: String?,
    var image_url: String?,
    var genre: String?,
    var country: String?,
    var raiting: Double?,
)
