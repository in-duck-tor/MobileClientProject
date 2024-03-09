package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model

import androidx.annotation.StringRes
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface AccountInfoEffect : BaseEffect {

    data class NavigateToTransactionScreen(
        val accountNumber: String,
    ) : AccountInfoEffect

    data class ShowError(
        @StringRes val stringResource: Int,
    ) : AccountInfoEffect
}