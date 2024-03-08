package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState

sealed interface MyAccountsMainState : BaseState {

    data object Loading : MyAccountsMainState

    data class Content(
        val clientId: String,
        val accounts: List<AccountInfo>,
        val filter: AccountsFilter = AccountsFilter.ALL,
    ) : MyAccountsMainState
}