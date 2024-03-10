package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource

class ObserveTransactionsUseCase(
    private val localDatasource: AccountsLocalDatasource,
) {

    suspend operator fun invoke(accountNumber: String) = provideResult {
        localDatasource.observeTransactionsByAccountNumber(accountNumber)
    }
}
