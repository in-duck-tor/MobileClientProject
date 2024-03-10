package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.domain.loans.model.Loan

data class MyLoansMainState(
    val loans: List<Loan>,
    val isLoading: Boolean,
)
