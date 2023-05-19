package com.example.skillcinema.model.data.apiFilter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country(
    val country: String,
    @PrimaryKey
    val id: Int,
)