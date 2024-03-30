package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class GetUserLoginUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke() = repository.getLogin()
}