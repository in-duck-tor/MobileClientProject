package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource

class ObserveAccountsUseCase(
    private val localDatasource: AccountsLocalDatasource,
) {

    suspend operator fun invoke(clientId: String) = provideResult {
        localDatasource.observeAccounts(clientId, filter = "")
    }
}