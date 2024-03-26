package com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface LoginEffect : BaseEffect {

    data object NavigateToRegistrationScreen : LoginEffect

    data object NavigateToMainScreen : LoginEffect

    data class ShowError(
        val message: String,
    ) : LoginEffect
}