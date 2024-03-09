package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.model

import androidx.annotation.StringRes
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface CreateAccountEffect : BaseEffect {

    data object CloseSelf : CreateAccountEffect

    data class ShowError(
        @StringRes val stringResource: Int,
        val message: String = "",
    ) : CreateAccountEffect

    data class ShowCreationToast(
        val message: String,
    ) : CreateAccountEffect
}