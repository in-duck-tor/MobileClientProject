package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsRemoteDatasource

class CreateAccountUseCase(
    private val remoteDatasource: AccountsRemoteDatasource,
    private val localDatasource: AccountsLocalDatasource,
) {

    suspend operator fun invoke(data: CreateAccount, token: String) = provideResult {
        val account = remoteDatasource.createAccount(data)
        localDatasource.insertAccount(account, token)
    }
}
