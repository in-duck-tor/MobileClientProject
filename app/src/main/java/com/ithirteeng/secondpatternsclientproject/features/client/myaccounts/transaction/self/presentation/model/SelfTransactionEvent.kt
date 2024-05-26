package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.Currency

sealed interface SelfTransactionEvent : BaseEvent {

    data class Init(val accountNumber: String) : SelfTransactionEvent

    data class DataLoaded(
        val depositAccount: Account,
        val accounts: List<Account>,
        val rates: List<Currency>,
    ) : SelfTransactionEvent

    sealed interface Ui : SelfTransactionEvent, BaseEvent.Ui {

        data class WithdrawButtonClick(val isSelf: Boolean) : Ui

        data object DepositButtonClick : Ui

        data class WithdrawAccountChoice(val account: Account) : Ui

        data class AmountValueChange(
            val amountText: TextFieldValue,
        ) : Ui

        data object CloseMoneyInfoDialog : Ui
    }
}
