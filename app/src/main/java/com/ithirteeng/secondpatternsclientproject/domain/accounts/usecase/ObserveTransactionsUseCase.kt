package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsLocalDatasource

class ObserveTransactionsUseCase(
    private val localDatasource: AccountsLocalDatasource,
) {

    suspend operator fun invoke(accountNumber: String) = provideResult {
        localDatasource.observeTransactionsByAccountNumber(accountNumber)
    }
}