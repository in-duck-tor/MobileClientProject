package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class SaveUserIdUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke() = repository.getUserId()
}