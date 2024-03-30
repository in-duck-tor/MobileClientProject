package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserRepository

class GetUserIdUseCase(
    private val userRepository: UserRepository,
) {

    operator fun invoke() = userRepository.getUserId()
}