package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

sealed interface TransactionState : BaseState {

    data object Loading : TransactionState

    data class Content(
        val defaultAccount: Account,
        val accounts: List<Account>,
        val chosenAccount: Account,
        val amount: Double = 0.0,
        val amountText: TextFieldValue = TextFieldValue("$amount"),
    ) : TransactionState
}