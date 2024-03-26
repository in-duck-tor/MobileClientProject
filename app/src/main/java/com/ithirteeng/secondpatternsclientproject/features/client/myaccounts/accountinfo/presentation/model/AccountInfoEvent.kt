package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Transaction

sealed interface AccountInfoEvent : BaseEvent {

    data class Init(val accountNumber: String) : AccountInfoEvent

    data class DataLoaded(
        val account: Account,
        val transactions: List<Transaction>,
    ) : AccountInfoEvent

    sealed interface Ui : AccountInfoEvent, BaseEvent.Ui {

        data class ChangeAccountState(val action: AccountAction) : Ui

        data object MakeTransactionButtonClick : Ui
    }
}