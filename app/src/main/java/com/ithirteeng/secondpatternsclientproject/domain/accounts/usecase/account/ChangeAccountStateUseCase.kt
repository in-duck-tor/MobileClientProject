package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsStubDatasource
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model.AccountAction

class ChangeAccountStateUseCase(
    private val localDatasource: AccountsLocalDatasource,
    private val remoteDatasource: AccountsRemoteDatasource,
) {

    suspend operator fun invoke(
        action: AccountAction,
        account: Account,
        token: String,
    ) = provideResult {
        when (action) {
            AccountAction.CLOSE -> remoteDatasource.closeAccount()
            AccountAction.FREEZE -> remoteDatasource.freezeAccount()
            AccountAction.UNFREEZE -> remoteDatasource.unfreezeAccount()
        }
        localDatasource.insertAccount(account, token)
    }
}