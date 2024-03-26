package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface TransactionEffect : BaseEffect {

    data object CloseSelf : TransactionEffect

    data class ShowError(
        val message: String,
    ) : TransactionEffect

    data class ShowCreationToast(
        val message: String,
    ) : TransactionEffect
}