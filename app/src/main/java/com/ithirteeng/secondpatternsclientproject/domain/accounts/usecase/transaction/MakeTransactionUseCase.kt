package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource

class MakeTransactionUseCase(
    private val remoteDatasource: AccountsRemoteDatasource,
) {

    suspend operator fun invoke(transactionRequest: TransactionRequest) = provideResult {
        remoteDatasource.makeTransaction(transactionRequest)
    }
}