package com.ithirteeng.secondpatternsclientproject.data.user.repository

import com.ithirteeng.secondpatternsclientproject.data.user.storage.TokenStorage
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class UserRepositoryImpl(
    private val tokenStorage: TokenStorage,
) : UserRepository {

    override fun saveToken(token: String) {
        tokenStorage.saveToken(token)
    }

    override fun getToken(): String {
        return tokenStorage.getToken()
    }
}
