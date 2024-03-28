package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface LoanInfoEffect : BaseEffect {

    data class ShowError(val message: String) : LoanInfoEffect
}