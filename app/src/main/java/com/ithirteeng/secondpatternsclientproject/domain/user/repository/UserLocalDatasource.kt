package com.ithirteeng.secondpatternsclientproject.domain.user.repository

import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token

interface UserLocalDatasource {

    fun saveToken(token: Token)

    fun getToken(): Token?

    fun saveLogin(login: String)

    fun getLogin(): String

    fun wipeData()

    fun getUserId(): String

    fun saveUserId(userId: String)
}
