package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction

sealed interface AccountInfoState : BaseState {

    data object Loading : AccountInfoState

    data class Content(
        val account: Account,
        val transactions: List<Transaction>,
        val actions: List<AccountAction>,
    ) : AccountInfoState
}