package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount

class CreateAccountV2UseCase(
    private val accountsRemoteDatasource: AccountsRemoteDatasource
) {

    suspend operator fun invoke(createAccount: CreateAccount) = provideResult {
        accountsRemoteDatasource.createAccountV2(createAccount)
    }
}