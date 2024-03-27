package com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

class FetchAccountsUseCase(
    private val localDatasource: AccountsLocalDatasource,
    private val remoteDatasource: AccountsRemoteDatasource,
) {

    suspend operator fun invoke(login: String) = provideResult {
        var accounts = remoteDatasource.getAccountsList()
        val hiddenAccounts = remoteDatasource.getHiddenAccountNumbers(login)
        hiddenAccounts?.let { hidden ->
            accounts = accounts.map {
                if (it.number in hidden) it.toHiddenAccount() else it
            }
        }

        localDatasource.fetchAccounts(accounts, login)
    }

    private fun Account.toHiddenAccount() = Account(
        number = number,
        currencyCode = currencyCode,
        amount = amount,
        state = state,
        customComment = customComment,
        isHidden = true

    )
}
