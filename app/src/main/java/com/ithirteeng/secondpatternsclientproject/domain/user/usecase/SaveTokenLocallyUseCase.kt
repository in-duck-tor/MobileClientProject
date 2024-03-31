package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserLocalDatasource

class SaveTokenLocallyUseCase(
    private val repository: UserLocalDatasource,
) {

    operator fun invoke(token: Token) {
        repository.saveToken(token)
    }
}