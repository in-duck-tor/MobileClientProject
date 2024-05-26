package com.ithirteeng.secondpatternsclientproject.domain.user.repository

import com.ithirteeng.secondpatternsclientproject.domain.user.model.AuthTokenResponse
import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAccount
import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAuthData

interface UserRemoteDatasource {

    suspend fun authorize(data: UserAuthData): AuthTokenResponse

    suspend fun getUserData(): UserAccount
}