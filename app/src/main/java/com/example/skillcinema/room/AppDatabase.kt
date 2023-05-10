package com.example.skillcinema.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skillcinema.model.data.db.MoviesHolder

@Database(entities = [MoviesHolder::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun typeDao(): TypeDao

}