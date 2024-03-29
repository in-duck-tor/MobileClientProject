package com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

sealed interface ProgramInfoEvent : BaseEvent {

    data class Init(val programId: Long) : ProgramInfoEvent

    data class DataLoaded(val program: LoanProgramResponse) : ProgramInfoEvent

    sealed interface Ui : ProgramInfoEvent, BaseEvent.Ui {

        data object CreateLoanButtonClick : Ui
    }
}