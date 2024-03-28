package com.ithirteeng.secondpatternsclientproject.data.user.storage

import android.content.Context
import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token

class TokenStorage(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: Token) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, token.accessToken)
            .putString(REFRESH_TOKEN_KEY, token.refreshToken)
            .apply()
    }

    fun getToken(): Token? {
        val authToken = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        val refreshToken = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
        return if (authToken != null && refreshToken != null) Token(
            authToken,
            refreshToken
        ) else null
    }

    fun saveLogin(login: String) {
        sharedPreferences.edit()
            .putString(LOGIN_KEY, login)
            .apply()
    }

    fun getLogin(): String {
        return sharedPreferences.getString(LOGIN_KEY, "").toString()
    }

    fun clearStorage() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit()
            .putString(USER_ID_KEY, userId)
            .apply()
    }

    fun getUserId(): String {
        return sharedPreferences.getString(USER_ID_KEY, "0").toString()
    }

    private companion object {
        const val USER_ID_KEY = "user id key"
        const val LOGIN_KEY = "login key"
        const val ACCESS_TOKEN_KEY = "access token key"
        const val REFRESH_TOKEN_KEY = "refresh token key"
        const val STORAGE_NAME = "token local storage"
    }
}
