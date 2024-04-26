package com.ithirteeng.secondpatternsclientproject.data.user.repository

import com.ithirteeng.secondpatternsclientproject.data.user.service.UserInfoService
import com.ithirteeng.secondpatternsclientproject.data.user.service.UserNetworkService
import com.ithirteeng.secondpatternsclientproject.domain.user.model.AuthTokenResponse
import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAccount
import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAuthData
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRemoteDatasource

class UserRemoteDatasourceImpl(
    private val networkService: UserNetworkService,
    private val userInfoService: UserInfoService,
) : UserRemoteDatasource {

    override suspend fun authorize(data: UserAuthData): AuthTokenResponse {
        return networkService.login(data)
    }


    override suspend fun getUserData(): UserAccount {
        return userInfoService.getUserData()
    }
}