package com.example.skillcinema.room

import androidx.room.*
import com.example.skillcinema.model.data.apiFilter.Country
import com.example.skillcinema.model.data.apiFilter.Genre

@Dao
interface FilterDao {

    @Query("SELECT * FROM country")
    suspend fun getAllCountry(): List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(vararg country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: List<Country>)

    @Delete
    suspend fun deleteCountry(country: Country)

    @Query("DELETE FROM country")
    fun deleteAllCountry()

    @Update
    fun update(country: Country)

    @Query("SELECT * FROM country WHERE country LIKE :searchQuery")
    suspend fun getCountryByKeyWord(searchQuery: String): List<Country>


    @Query("SELECT * FROM genre")
    suspend fun getAllGenre(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(vararg genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: List<Genre>)

    @Delete
    suspend fun deleteGenre(genre: Genre)

    @Query("DELETE FROM genre")
    fun deleteAllGenre()

    @Update
    fun update(genre: Genre)

    @Query("SELECT * FROM genre WHERE genre LIKE :searchQuery")
    suspend fun getGenreByKeyWord(searchQuery: String): List<Genre>

}