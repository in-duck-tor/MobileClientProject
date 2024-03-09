package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsRemoteDatasource

class CreateAccountUseCase(
    private val remoteDatasource: AccountsRemoteDatasource,
) {

    suspend operator fun invoke(data: CreateAccount) = provideResult {
        remoteDatasource.createAccount(data)
    }
}
