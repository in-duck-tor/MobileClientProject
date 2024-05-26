package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

class MakeAccountHiddenUseCase(
    private val remoteDatasource: AccountsRemoteDatasource,
    private val localDatasource: AccountsLocalDatasource,
) {

    suspend operator fun invoke(login: String, changedAccount: Account) = provideResult {
        remoteDatasource.addHiddenAccount(login, changedAccount.number)
        localDatasource.insertAccount(changedAccount, login)
    }
}