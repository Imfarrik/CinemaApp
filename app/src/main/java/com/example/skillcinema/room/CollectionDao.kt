package com.example.skillcinema.room

import androidx.room.*
import com.example.skillcinema.model.data.apiFilter.Country
import com.example.skillcinema.model.data.apiFilter.Genre
import com.example.skillcinema.model.data.db.*

@Dao
interface CollectionDao {

    @Query("SELECT * FROM collection")
    suspend fun getAllCollections(): List<CollectionHolder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(vararg collectionHolder: CollectionHolder)

    @Delete
    suspend fun deleteCollection(collectionHolder: CollectionHolder)

    //  ------------------------------------------------------------------------------------------------

    @Query("SELECT * FROM like")
    suspend fun getAllLike(): List<LikeHolder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedMovie(vararg likeHolder: LikeHolder)

    @Query("DELETE FROM like WHERE movie_id = :movieId")
    suspend fun deleteLikedMovie(movieId: Int)

    @Delete
    suspend fun deleteLikedMovie(likeHolder: LikeHolder)

    //  ------------------------------------------------------------------------------------------------

    @Query("SELECT * FROM watched")
    suspend fun getAllWatched(): List<WatchedHolder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchedMovie(vararg watchedHolder: WatchedHolder)

    @Query("DELETE FROM watched WHERE movie_id = :movieId")
    suspend fun deleteWatchedMovie(movieId: Int)

    @Delete
    suspend fun deleteWatchedMovie(watchedHolder: WatchedHolder)

    //  ------------------------------------------------------------------------------------------------

    @Query("SELECT * FROM interested")
    suspend fun getAllInterested(): List<WasInterestedHolder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterestedMovie(vararg watchedHolder: WasInterestedHolder)

    @Query("DELETE FROM interested WHERE movie_id = :movieId")
    suspend fun deleteInterestedMovie(movieId: Int)

    @Query("DELETE FROM interested")
    suspend fun deleteAllInterestedMovie()

    //  ------------------------------------------------------------------------------------------------

    @Query("SELECT * FROM save")
    suspend fun getAllSavedMovie(): List<SaveHolder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedMovie(vararg saveHolder: SaveHolder)

    @Query("DELETE FROM save WHERE movie_id = :movieId")
    suspend fun deleteSavedMovie(movieId: Int)

    @Delete
    suspend fun deleteSavedMovie(saveHolder: SaveHolder)

    //  ------------------------------------------------------------------------------------------------

    @Query("SELECT * FROM movie_in_collection")
    suspend fun getAllSavedMovieInCollection(): List<MoviesInCollection>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieInCollection(vararg moviesInCollection: MoviesInCollection)

    @Delete
    suspend fun deleteMovieInCollection(collectionHolder: MoviesInCollection)

    @Query("DELETE FROM movie_in_collection WHERE movie_id = :movieId AND collection_name = :collectionName")
    suspend fun deleteMovieInCollection(movieId: Int, collectionName: String)

    @Query("SELECT * FROM movie_in_collection WHERE collection_id LIKE :searchQuery")
    suspend fun getMovieInCollection(searchQuery: Int): List<MoviesInCollection>

    @Query("SELECT * FROM movie_in_collection WHERE collection_name LIKE :searchQuery")
    suspend fun getMovieInCollection(searchQuery: String): List<MoviesInCollection>


}