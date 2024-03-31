package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoShort
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

sealed interface MyLoansMainState : BaseState {

    data object Loading : MyLoansMainState

    data class Content(
        val programs: List<LoanProgramResponse>,
        val loans: List<LoanInfoShort>,
        val creditScore: Int,
    ) : MyLoansMainState
}
