package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model

import androidx.annotation.StringRes
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface MyAccountsMainEffect: BaseEffect {

    data class NavigateToAccountInfoScreen(
        val clientId: String,
        val accountId: String,
    ) : MyAccountsMainEffect

    data class NavigateToCreateAccountScreen(
        val clientId: String,
    ) : MyAccountsMainEffect

    data class ShowError(
        @StringRes val stringResource: Int,
    ) : MyAccountsMainEffect
}