package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoResponse

sealed interface LoanInfoEvent : BaseEvent {

    data class Init(val loanId: Int) : LoanInfoEvent

    data class DataLoaded(val loanInfo: LoanInfoResponse) : LoanInfoEvent

    sealed interface Ui : BaseEvent.Ui, LoanInfoEvent {

        data object MakePaymentButtonClick : Ui
    }
}