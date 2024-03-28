package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoResponse

sealed interface LoanInfoState: BaseState {

    data object Loading : LoanInfoState

    data class Content(
        val loanInfo: LoanInfoResponse,
        val amount: TextFieldValue = TextFieldValue("")
    ): LoanInfoState
}