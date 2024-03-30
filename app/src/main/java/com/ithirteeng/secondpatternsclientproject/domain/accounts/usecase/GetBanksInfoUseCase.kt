package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource

class GetBanksInfoUseCase(
    private val remoteDatasource: AccountsRemoteDatasource,
) {

    suspend operator fun invoke() = provideResult {
        remoteDatasource.getBanksInfo()
    }
}
