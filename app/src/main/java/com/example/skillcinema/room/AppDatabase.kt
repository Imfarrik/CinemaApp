package com.example.skillcinema.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skillcinema.model.data.apiFilter.Country
import com.example.skillcinema.model.data.apiFilter.Genre
import com.example.skillcinema.model.data.db.*

@Database(
    entities = [MoviesHolder::class,
        Country::class, Genre::class,
        CollectionHolder::class,
        LikeHolder::class,
        MoviesInCollection::class,
        SaveHolder::class,
        WatchedHolder::class,
        WasInterestedHolder::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun typeDao(): TypeDao
    abstract fun filterDao(): FilterDao
    abstract fun collectionDao(): CollectionDao

}