package com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

sealed interface ProgramInfoState: BaseState {

    data object Loading: ProgramInfoState

    data class Content(
        val program: LoanProgramResponse
    ) : ProgramInfoState
}