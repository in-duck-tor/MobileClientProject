package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class GetLocalTokenUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke(): String {
        return repository.getToken()
    }
}
