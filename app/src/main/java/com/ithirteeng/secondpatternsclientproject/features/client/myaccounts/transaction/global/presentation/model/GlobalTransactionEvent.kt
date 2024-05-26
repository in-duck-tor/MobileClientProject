package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.bank.Bank

sealed interface GlobalTransactionEvent : BaseEvent {

    data class Init(val accountId: String) : GlobalTransactionEvent

    sealed interface Ui : BaseEvent.Ui, GlobalTransactionEvent {

        data object MakeTransactionButtonClick : Ui

        data class AccountNumberChange(val value: TextFieldValue) : Ui

        data class AmountValueChange(val value: TextFieldValue) : Ui

        data class BankChoice(val bank: Bank) : Ui
    }
}