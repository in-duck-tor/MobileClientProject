package com.ithirteeng.secondpatternsclientproject.data.user.repository

import com.ithirteeng.secondpatternsclientproject.data.user.storage.TokenStorage
import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserLocalDatasource

class UserLocalDatasourceImpl(
    private val tokenStorage: TokenStorage,
) : UserLocalDatasource {

    override fun saveToken(token: Token) {
        tokenStorage.saveToken(token)
    }

    override fun getToken(): Token? {
        return tokenStorage.getToken()
    }

    override fun saveLogin(login: String) {
        tokenStorage.saveLogin(login)
    }

    override fun getLogin(): String {
        return tokenStorage.getLogin()
    }

    override fun wipeData() {
        tokenStorage.clearStorage()
    }

    override fun getUserId(): String {
        return tokenStorage.getUserId()
    }

    override fun saveUserId(userId: String) {
        tokenStorage.saveUserId(userId)
    }
}