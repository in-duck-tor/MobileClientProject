package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class GetLocalTokenUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke(): Token? {
        return repository.getToken()
    }
}
