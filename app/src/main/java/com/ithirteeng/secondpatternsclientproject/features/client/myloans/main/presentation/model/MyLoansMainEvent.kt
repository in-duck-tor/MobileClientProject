package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoShort
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

sealed interface MyLoansMainEvent: BaseEvent {

    data object Init : MyLoansMainEvent

    data class DataLoaded(
        val programs: List<LoanProgramResponse>,
        val loans: List<LoanInfoShort>,
    ) : MyLoansMainEvent

    sealed interface Ui : MyLoansMainEvent, BaseEvent.Ui {

        data class LoanProgramClick(val programId: Int) : Ui

        data class LoanClick(val loanId: Int) : Ui
    }
}