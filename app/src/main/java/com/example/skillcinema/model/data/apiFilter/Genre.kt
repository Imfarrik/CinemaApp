package com.example.skillcinema.model.data.apiFilter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Genre(
    val genre: String,
    @PrimaryKey
    val id: Int,
)