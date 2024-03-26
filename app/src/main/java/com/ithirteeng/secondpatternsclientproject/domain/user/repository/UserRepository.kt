package com.ithirteeng.secondpatternsclientproject.domain.user.repository

import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token

interface UserRepository {

    fun saveToken(token: Token)

    fun getToken(): Token

    fun saveLogin(login: String)

    fun getLogin(): String
}
