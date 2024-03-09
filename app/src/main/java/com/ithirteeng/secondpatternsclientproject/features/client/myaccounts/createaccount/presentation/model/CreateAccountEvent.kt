package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account

sealed interface CreateAccountEvent : BaseEvent {

    data object Init : CreateAccountEvent

    data class DataReceived(
        val account: Account,
    )

    sealed interface Ui : CreateAccountEvent, BaseEvent.Ui {

        data object CreateAccountButtonClick : Ui

        data class CommentTextChanged(val text: TextFieldValue) : Ui
    }

}