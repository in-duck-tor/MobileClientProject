package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsStubDatasource

class WithdrawFromAccountStubUseCase(
    private val stubDatasource: AccountsStubDatasource
) {

    suspend operator fun invoke(
        accountNumber: String,
        amount: Double,
        token: String,
    ) = provideResult {
        stubDatasource.withdrawFromAccountStub(accountNumber, amount, token)
    }
}