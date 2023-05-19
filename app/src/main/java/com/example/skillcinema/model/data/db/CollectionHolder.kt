package com.example.skillcinema.model.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection")
data class CollectionHolder(
    var name: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
)
