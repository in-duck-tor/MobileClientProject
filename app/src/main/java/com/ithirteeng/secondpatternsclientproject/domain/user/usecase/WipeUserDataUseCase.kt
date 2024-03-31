package com.ithirteeng.secondpatternsclientproject.domain.user.usecase

import com.ithirteeng.secondpatternsclientproject.domain.user.repository.UserLocalDatasource

class WipeUserDataUseCase(
    private val repository: UserLocalDatasource,
) {

    operator fun invoke() = repository.wipeData()
}