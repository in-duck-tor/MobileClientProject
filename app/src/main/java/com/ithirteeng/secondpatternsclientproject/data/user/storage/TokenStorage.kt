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

    fun getToken(): Token {
        val authToken = sharedPreferences.getString(ACCESS_TOKEN_KEY, "").toString()
        val refreshToken = sharedPreferences.getString(REFRESH_TOKEN_KEY, "").toString()
        return Token(authToken, refreshToken)
    }

    fun saveLogin(login: String) {
        sharedPreferences.edit()
            .putString(LOGIN_KEY, login)
            .apply()
    }

    fun getLogin(): String {
        return sharedPreferences.getString(LOGIN_KEY, "").toString()
    }

    private companion object {
        const val LOGIN_KEY = "login key"
        const val ACCESS_TOKEN_KEY = "access token key"
        const val REFRESH_TOKEN_KEY = "refresh token key"
        const val STORAGE_NAME = "token local storage"
    }
}
