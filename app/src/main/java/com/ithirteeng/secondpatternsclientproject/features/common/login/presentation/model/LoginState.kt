package com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState

sealed interface LoginState : BaseState {

    data object Init : LoginState

    data class Content(
        val login: TextFieldValue,
        val password: TextFieldValue,
    ): LoginState
}
