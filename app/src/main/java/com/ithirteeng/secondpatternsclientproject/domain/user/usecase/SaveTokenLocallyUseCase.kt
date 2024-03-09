package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class SaveTokenLocallyUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke(token: String) {
        repository.saveToken(token)
    }
}