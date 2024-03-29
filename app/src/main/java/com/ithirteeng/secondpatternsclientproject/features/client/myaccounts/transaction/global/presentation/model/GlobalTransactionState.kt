package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

sealed interface GlobalTransactionState : BaseState {

    data object Loading : GlobalTransactionState

    data class Content(
        val account: Account,
        val accountNumber: TextFieldValue = TextFieldValue(""),
        val amount: Double = 0.0,
        val amountText: TextFieldValue = TextFieldValue("$amount"),
        //  val isTransactionInfoDialogOpen: Boolean = false,
    ) : GlobalTransactionState
}