package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState

sealed interface CreateAccountState : BaseState {

    data object Loading : CreateAccountState

    data class Content(
        val customComment: TextFieldValue = TextFieldValue(""),
        val currencyCode: String = "RUB",
    ) : CreateAccountState

}