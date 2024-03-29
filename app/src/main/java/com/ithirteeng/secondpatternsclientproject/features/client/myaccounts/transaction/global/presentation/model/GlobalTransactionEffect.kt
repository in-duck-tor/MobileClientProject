package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface GlobalTransactionEffect : BaseEffect {

    data class ShowError(val message: String) : GlobalTransactionEffect

    data object CloseSelf : GlobalTransactionEffect
}