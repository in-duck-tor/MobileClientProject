package com.ithirteeng.secondpatternsclientproject.data.user.storage

import android.content.Context

class TokenStorage(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    fun getToken(): String {
        return sharedPreferences.getString(TOKEN_KEY, "").toString()
    }

    private companion object {
        const val TOKEN_KEY = "token key"
        const val STORAGE_NAME = "token local storage"
    }
}
