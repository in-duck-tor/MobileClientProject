package com.ithirteeng.secondpatternsclientproject.data.user.repository

import com.ithirteeng.secondpatternsclientproject.data.user.service.UserNetworkService
import com.ithirteeng.secondpatternsclientproject.domain.user.model.AuthTokenResponse
import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAuthData
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRemoteDatasource

class UserRemoteDatasourceImpl(
    private val networkService: UserNetworkService,
): UserRemoteDatasource {

    override suspend fun authorize(data: UserAuthData): AuthTokenResponse {
        return networkService.login(data)
    }
}