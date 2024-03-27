package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.Currency
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.CurrencyRate

sealed interface SelfTransactionState : BaseState {

    data object Loading : SelfTransactionState

    data class Content(
        val defaultAccount: Account,
        val accounts: List<Account>,
        val chosenAccount: Account,
        val amount: Double = 0.0,
        val amountText: TextFieldValue = TextFieldValue("$amount"),
        val isTransactionInfoDialogOpen: Boolean = false,
        val finishAction: SelfTransactionAction? = null,
        val currencyRates: List<Currency>,
        val amountForTransaction: Double? = null,
    ) : SelfTransactionState
}