package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.base.Loan

data class LoanInfoState(
    val loan: Loan? = null,
    val isLoading: Boolean = true,
    val isDeletedButtonClick: Boolean = false,
    val isDeleted: Boolean = false,
)
