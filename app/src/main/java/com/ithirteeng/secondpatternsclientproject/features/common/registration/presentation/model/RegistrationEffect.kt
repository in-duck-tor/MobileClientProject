package com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.model

import androidx.annotation.StringRes
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface RegistrationEffect : BaseEffect {

    data object NavigateToLoginScreen : RegistrationEffect

    data object NavigateToMainScreen : RegistrationEffect

    data class ShowError(
        @StringRes val stringResource: Int,
    ) : RegistrationEffect
}
