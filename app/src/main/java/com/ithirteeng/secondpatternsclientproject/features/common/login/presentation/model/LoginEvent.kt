package com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent

sealed interface LoginEvent : BaseEvent {

    data object Init : LoginEvent

    sealed interface Ui : LoginEvent, BaseEvent.Ui {

        data object LoginButtonClick : Ui

        data object RegistrationButtonClick : Ui

        data class LoginFieldChange(val value: TextFieldValue) : Ui

        data class PasswordFieldChange(val value: TextFieldValue) : Ui
    }
}
