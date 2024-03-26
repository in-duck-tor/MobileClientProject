package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class SaveTokenLocallyUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke(token: Token) {
        repository.saveToken(token)
    }
}