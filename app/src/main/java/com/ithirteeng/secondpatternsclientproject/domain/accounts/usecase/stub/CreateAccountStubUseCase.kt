package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsStubDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.CreateAccount

class CreateAccountStubUseCase(
    private val stubDatasource: AccountsStubDatasource,
) {

    suspend operator fun invoke(data: CreateAccount, token: String) = provideResult {
        stubDatasource.createAccountStub(data, token)
    }
}
