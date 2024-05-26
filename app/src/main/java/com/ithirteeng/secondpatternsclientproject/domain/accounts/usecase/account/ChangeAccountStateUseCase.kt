package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
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
            AccountAction.CLOSE -> remoteDatasource.closeAccountV2(account.number)
            AccountAction.FREEZE -> remoteDatasource.freezeAccountV2(account.number)
            AccountAction.UNFREEZE -> remoteDatasource.unfreezeAccountV2(account.number)
        }
        localDatasource.insertAccount(account, token)
    }
}