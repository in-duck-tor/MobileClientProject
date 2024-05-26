package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface SelfTransactionEffect : BaseEffect {

    data object CloseSelf : SelfTransactionEffect

    data class ShowError(
        val message: String,
    ) : SelfTransactionEffect

    data class ShowCreationToast(
        val message: String,
    ) : SelfTransactionEffect
}