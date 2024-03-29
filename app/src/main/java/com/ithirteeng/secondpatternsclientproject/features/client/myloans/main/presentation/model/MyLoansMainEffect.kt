package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface MyLoansMainEffect : BaseEffect {

    data class NavigateToProgramInfoScreen(val programId: Long) : MyLoansMainEffect

    data class NavigateToLoanInfoScreen(val loanId: Long) : MyLoansMainEffect

    data class ShowError(val message: String) : MyLoansMainEffect
}