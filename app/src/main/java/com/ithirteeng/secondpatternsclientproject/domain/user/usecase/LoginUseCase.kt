package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.model.UserAuthData
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRemoteDatasource

class LoginUseCase(
    private val remoteDatasource: UserRemoteDatasource,
) {

    suspend operator fun invoke(userAuthData: UserAuthData) =
        remoteDatasource.authorize(userAuthData)
}