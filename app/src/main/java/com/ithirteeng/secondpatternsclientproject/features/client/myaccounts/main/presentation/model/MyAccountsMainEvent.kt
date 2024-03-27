package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

sealed interface MyAccountsMainEvent : BaseEvent {

    data class Init(val clientId: String) : MyAccountsMainEvent

    data class DataLoaded(val clientId: String, val accounts: List<Account>) : MyAccountsMainEvent

    sealed interface Ui : MyAccountsMainEvent, BaseEvent.Ui {

        data object CreateAccountButtonClick : Ui

        data class AccountClick(val accountId: String) : Ui

        data class AccountsFilterChange(val filter: AccountsFilter) : Ui

        data class HiddenAccountVisibilityChange(val isVisible: Boolean) : Ui

        data class ChangeAccountVisibility(val account: Account): Ui
    }
}