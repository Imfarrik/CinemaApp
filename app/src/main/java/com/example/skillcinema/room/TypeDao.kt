package com.example.skillcinema.room

import androidx.room.*
import com.example.skillcinema.model.data.db.MoviesHolder

@Dao
interface TypeDao {

    @Query("SELECT * FROM movies_holder")
    suspend fun getAll(): List<MoviesHolder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg moviesHolder: MoviesHolder)

    @Delete
    suspend fun delete(moviesHolder: MoviesHolder)

    @Delete
    fun delete(moviesHolder: Array<MoviesHolder>)

    @Query("DELETE FROM movies_holder")
    fun deleteAll()

    @Update
    fun update(moviesHolder: MoviesHolder)

}