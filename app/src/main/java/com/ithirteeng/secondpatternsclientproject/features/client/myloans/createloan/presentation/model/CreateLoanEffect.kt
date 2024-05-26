package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface CreateLoanEffect : BaseEffect {

    data class ShowError(val message: String) : CreateLoanEffect

    data object CloseSelf : CreateLoanEffect
}