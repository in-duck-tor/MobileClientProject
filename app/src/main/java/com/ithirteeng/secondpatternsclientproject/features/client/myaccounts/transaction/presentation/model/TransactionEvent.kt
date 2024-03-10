package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

sealed interface TransactionEvent : BaseEvent {

    data class Init(val accountNumber: String) : TransactionEvent

    data class DataLoaded(
        val depositAccount: Account,
        val accounts: List<Account>,
    ) : TransactionEvent

    sealed interface Ui : TransactionEvent, BaseEvent.Ui {

        data object WithdrawButtonClick : Ui

        data object DepositButtonClick : Ui

        data class WithdrawAccountChoice(val account: Account) : Ui

        data class AmountValueChange(
            val amountText: TextFieldValue,
        ) : Ui
    }
}
