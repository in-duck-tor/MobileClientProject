package com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface AuthEffect: BaseEffect {
    data object NavigateToMainScreen : AuthEffect

    data class ShowError(
        val message: String,
    ) : AuthEffect
}