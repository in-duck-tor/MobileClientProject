package com.ithirteeng.secondpatternsclientproject.domain.user.repository

interface UserRepository {

    fun saveToken(token: String)

    fun getToken(): String
}
