package com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState

sealed interface RegistrationState : BaseState {

    data object Init : RegistrationState

    data class Content(
        val login: TextFieldValue,
        val email: TextFieldValue,
        val password: TextFieldValue,
        val repeatPassword: TextFieldValue,
    ) : RegistrationState
}