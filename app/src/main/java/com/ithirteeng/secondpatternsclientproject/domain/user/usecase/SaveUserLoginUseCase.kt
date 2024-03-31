package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserLocalDatasource

class SaveUserLoginUseCase(
    private val repository: UserLocalDatasource,
) {

    operator fun invoke(login: String) = repository.saveLogin(login)
}