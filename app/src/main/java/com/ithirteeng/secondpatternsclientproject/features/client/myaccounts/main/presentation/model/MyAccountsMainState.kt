package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState

sealed interface MyAccountsMainState : BaseState {

    data object Loading : MyAccountsMainState

    data class Content(
        val clientId: String,
        val accounts: List<Account>,
        val filter: AccountsFilter = AccountsFilter.ALL,
        val filterState: AccountState? = null,
        val showHidden: Boolean = true,
    ) : MyAccountsMainState
}