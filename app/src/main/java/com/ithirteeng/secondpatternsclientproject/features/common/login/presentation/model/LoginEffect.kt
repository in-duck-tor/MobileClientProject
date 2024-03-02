package com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.model

import androidx.annotation.StringRes
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface LoginEffect : BaseEffect {

    data object NavigateToRegistrationScreen : LoginEffect

    data object NavigateToMainScreen : LoginEffect

    data class ShowError(
        @StringRes val stringResource: Int,
    ) : LoginEffect
}