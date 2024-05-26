package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserLocalDatasource

class SaveUserIdUseCase(
    private val repository: UserLocalDatasource,
) {

    operator fun invoke() = repository.getUserId()
}