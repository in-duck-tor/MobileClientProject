package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserLocalDatasource

class GetUserIdUseCase(
    private val userLocalDatasource: UserLocalDatasource,
) {

    operator fun invoke() = userLocalDatasource.getUserId()
}