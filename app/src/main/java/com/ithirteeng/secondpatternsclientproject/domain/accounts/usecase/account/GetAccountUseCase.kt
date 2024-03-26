package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource

class GetAccountUseCase(
    private val localDatasource: AccountsLocalDatasource,
) {

    suspend operator fun invoke(accountNumber: String) = provideResult {
        localDatasource.getAccount(accountNumber)
    }
}