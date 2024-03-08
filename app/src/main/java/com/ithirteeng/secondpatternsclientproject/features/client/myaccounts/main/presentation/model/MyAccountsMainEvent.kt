package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent

sealed interface MyAccountsMainEvent : BaseEvent {

    data class Init(val clientId: String) : MyAccountsMainEvent

    data class DataLoaded(val clientId: String, val data: List<AccountInfo>) : MyAccountsMainEvent

    sealed interface Ui : MyAccountsMainEvent, BaseEvent.Ui {

        data object CreateAccountButtonClick : Ui

        data class AccountClick(val accountId: String) : Ui

        data class AccountsFilterChange(val filter: AccountsFilter): Ui
    }
}