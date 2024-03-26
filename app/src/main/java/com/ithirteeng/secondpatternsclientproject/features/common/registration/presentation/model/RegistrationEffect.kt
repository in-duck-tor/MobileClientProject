package com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface RegistrationEffect : BaseEffect {

    data object NavigateToLoginScreen : RegistrationEffect

    data object NavigateToMainScreen : RegistrationEffect

    data class ShowError(
        val message: String,
    ) : RegistrationEffect
}
