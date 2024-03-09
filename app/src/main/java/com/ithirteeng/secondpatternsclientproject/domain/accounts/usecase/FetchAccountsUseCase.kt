package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsRemoteDatasource

class FetchAccountsUseCase(
    private val localDatasource: AccountsLocalDatasource,
    private val remoteDatasource: AccountsRemoteDatasource,
) {

    suspend operator fun invoke(clientId: String) = provideResult {
        val accounts = remoteDatasource.getAccountsList()
        localDatasource.fetchAccounts(accounts, clientId)
    }
}
