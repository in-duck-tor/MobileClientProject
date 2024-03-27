package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

sealed interface GlobalTransactionState {

    data object Loading : GlobalTransactionState

    data class ContentLogin(
        val loginTextFieldValue: TextFieldValue,
        val accountNumber: String,
    ) : GlobalTransactionState

    data class ContentMain(
        val anotherUserLogin: String,
        val accountNumber: String,
        val anotherAccounts: List<Account>,
        val amount: Double = 0.0,
        val amountText: TextFieldValue = TextFieldValue("$amount"),
        val isTransactionInfoDialogOpen: Boolean = false,
    ) : GlobalTransactionState
}