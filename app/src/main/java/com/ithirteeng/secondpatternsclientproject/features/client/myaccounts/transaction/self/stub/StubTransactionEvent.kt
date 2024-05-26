package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.stub

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

sealed interface StubTransactionEvent : BaseEvent {

    data class Init(val accountNumber: String) : StubTransactionEvent

    data class DataLoaded(
        val depositAccount: Account,
        val accounts: List<Account>,
    ) : StubTransactionEvent

    sealed interface Ui : StubTransactionEvent, BaseEvent.Ui {

        data object WithdrawButtonClick : Ui

        data object DepositButtonClick : Ui

        data object TransferButtonClick : Ui

        data class WithdrawAccountChoice(val account: Account) : Ui

        data class AmountValueChange(
            val amountText: TextFieldValue,
        ) : Ui
    }
}