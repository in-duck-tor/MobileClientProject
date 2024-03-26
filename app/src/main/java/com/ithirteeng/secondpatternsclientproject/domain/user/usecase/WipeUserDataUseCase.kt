package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class WipeUserDataUseCase(
    private val repository: UserRepository,
) {

    operator fun invoke() = repository.wipeData()
}