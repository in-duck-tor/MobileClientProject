package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.base.Loan

data class MyLoansMainState(
    val loans: List<Loan>,
    val isLoading: Boolean,
)
