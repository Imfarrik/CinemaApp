package com.example.skillcinema.model.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_in_collection")
data class MoviesInCollection(
    var movie_id: Int,
    var name_ru: String?,
    var name_en: String?,
    var image_url: String?,
    var genre: String?,
    var country: String?,
    var raiting: Double?,
    var collection_id: Int,
    var collection_name: String,
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
