package com.example.skillcinema.domain

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    companion object {

        const val PREFERENCE_NAME = "manager"
        const val USER_TOKEN = "user_token"
        const val IS_WAS_OPENED = "is_was_opened"

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


}