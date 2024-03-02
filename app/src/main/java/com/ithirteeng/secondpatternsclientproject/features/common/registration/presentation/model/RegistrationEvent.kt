package com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent

sealed interface RegistrationEvent : BaseEvent {

    data object Init : RegistrationEvent

    sealed interface Ui : RegistrationEvent, BaseEvent.Ui {

        data object RegisterButtonClick : Ui

        data object LoginButtonClick : Ui

        data class LoginFieldChange(val value: TextFieldValue) : Ui

        data class PasswordFieldChange(val value: TextFieldValue) : Ui

        data class RepeatPasswordFieldChange(val value: TextFieldValue) : Ui
    }
}