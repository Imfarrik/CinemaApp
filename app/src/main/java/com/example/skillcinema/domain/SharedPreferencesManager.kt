package com.example.skillcinema.domain

import android.content.Context
import android.content.SharedPreferences
import com.example.skillcinema.model.data.apiFilter.Genre

class SharedPreferencesManager(context: Context) {

    companion object {

        const val PREFERENCE_NAME = "manager"
        const val USER_TOKEN = "user_token"
        const val IS_WAS_OPENED = "is_was_opened"
        const val COUNTRY_ID = "country_id"
        const val GENRES_ID = "genres_id"
        const val MOVIE_ID = "movie_id"


        const val COUNTRY = "COUNTRY"
        const val GENRE = "GENRE"
        const val ORDER = "ORDER"
        const val TYPE = "TYPE"
        const val RATING_FROM = "RATING_FROM"
        const val RATING_TO = "RATING_TO"
        const val YEAR_FROM = "YEAR_FROM"
        const val YEAR_TO = "YEAR_TO"
        const val IMDB_ID = "IMDB_ID"

    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = preferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return preferences.getString(USER_TOKEN, null)
    }

    fun isWasOpened(boolean: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_WAS_OPENED, boolean)
        editor.apply()
    }

    fun getWasOpened(): Boolean {
        return preferences.getBoolean(IS_WAS_OPENED, false)
    }

    fun saveCountryId(id: Int) {
        val editor = preferences.edit()
        editor.apply {
            putInt(COUNTRY_ID, id)
            apply()
        }
    }

    fun getCountryId(): Int {
        return preferences.getInt(COUNTRY_ID, 1)
    }

    fun saveGenreId(id: Int) {
        val editor = preferences.edit()
        editor.apply {
            putInt(GENRES_ID, id)
            apply()
        }
    }

    fun getGenreId(): Int {
        return preferences.getInt(GENRES_ID, 1)
    }

    fun saveMovieId(id: Int) {
        val editor = preferences.edit()
        editor.apply {
            putInt(MOVIE_ID, id)
            apply()
        }
    }

    fun getMovieId(): Int {
        return preferences.getInt(MOVIE_ID, 1)
    }

    fun saveCountry(id: String) {
        val editor = preferences.edit()
        editor.apply {
            putString(COUNTRY, id)
            apply()
        }
    }

    fun getCountry(): String? {
        return preferences.getString(COUNTRY, null)
    }

    fun saveGenre(id: String) {
        val editor = preferences.edit()
        editor.apply {
            putString(GENRE, id)
            apply()
        }
    }

    fun getGenre(): String? {
        return preferences.getString(GENRE, null)
    }

    fun saveOrder(id: String?) {
        val editor = preferences.edit()
        editor.apply {
            putString(ORDER, id)
            apply()
        }
    }

    fun getOrder(): String? {
        return preferences.getString(ORDER, null)
    }

    fun saveType(id: String?) {
        val editor = preferences.edit()
        editor.apply {
            putString(TYPE, id)
            apply()
        }
    }

    fun getType(): String? {
        return preferences.getString(TYPE, null)
    }

    fun saveRatingFrom(id: String) {
        val editor = preferences.edit()
        editor.apply {
            putString(RATING_FROM, id)
            apply()
        }
    }

    fun getRatingFrom(): String? {
        return preferences.getString(RATING_FROM, null)
    }

    fun saveRatingTo(id: String) {
        val editor = preferences.edit()
        editor.apply {
            putString(RATING_TO, id)
            apply()
        }
    }

    fun getRatingTo(): String? {
        return preferences.getString(RATING_TO, null)
    }

    fun saveYearFrom(id: String) {
        val editor = preferences.edit()
        editor.apply {
            putString(YEAR_FROM, id)
            apply()
        }
    }

    fun getYearFrom(): String? {
        return preferences.getString(YEAR_FROM, null)
    }

    fun saveYearTo(id: String) {
        val editor = preferences.edit()
        editor.apply {
            putString(YEAR_TO, id)
            apply()
        }
    }

    fun getYearTo(): String? {
        return preferences.getString(YEAR_TO, null)
    }

    fun saveImdbId(id: String) {
        val editor = preferences.edit()
        editor.apply {
            putString(IMDB_ID, id)
            apply()
        }
    }

    fun getImdbId(): String? {
        return preferences.getString(IMDB_ID, null)
    }


}