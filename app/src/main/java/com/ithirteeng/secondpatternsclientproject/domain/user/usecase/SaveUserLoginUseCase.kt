package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class SaveUserLoginUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke(login: String) = repository.saveLogin(login)
}