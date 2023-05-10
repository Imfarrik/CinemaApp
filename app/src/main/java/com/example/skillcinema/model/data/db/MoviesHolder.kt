package com.example.skillcinema.model.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_holder")
data class MoviesHolder(
    var type: String,

    @PrimaryKey
    val id: Int
)
