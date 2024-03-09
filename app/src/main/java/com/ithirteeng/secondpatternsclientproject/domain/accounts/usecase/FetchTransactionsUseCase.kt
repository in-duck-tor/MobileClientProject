package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsRemoteDatasource

class FetchTransactionsUseCase(
    private val remoteDatasource: AccountsRemoteDatasource,
    private val localDatasource: AccountsLocalDatasource,
) {

    suspend operator fun invoke(accountNumber: String) = provideResult {
        val transactions = remoteDatasource.getAccountTransactions(accountNumber)
        localDatasource.insertTransactions(transactions)
    }
}