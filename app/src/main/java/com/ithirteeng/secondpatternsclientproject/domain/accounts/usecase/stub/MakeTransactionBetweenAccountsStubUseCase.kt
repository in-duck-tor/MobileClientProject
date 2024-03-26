package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsStubDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest

class MakeTransactionBetweenAccountsStubUseCase(
    private val stubDatasource: AccountsStubDatasource,
) {

    suspend operator fun invoke(transactionRequest: TransactionRequest, token: String) =
        provideResult {
            stubDatasource.makeTransactionStub(transactionRequest, token)
        }
}