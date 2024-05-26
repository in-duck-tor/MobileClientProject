package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRemoteDatasource

class GetUserAccountUseCase(
    private val remoteDatasource: UserRemoteDatasource,
) {

    suspend operator fun invoke() = remoteDatasource.getUserData()
}