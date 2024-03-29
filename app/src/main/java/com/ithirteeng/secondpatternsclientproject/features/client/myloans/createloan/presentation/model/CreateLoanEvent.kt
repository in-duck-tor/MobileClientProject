package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

sealed interface CreateLoanEvent : BaseEvent {

    data class Init(val programId: Long) : CreateLoanEvent

    data class DataLoaded(
        val program: LoanProgramResponse,
        val accounts: List<Account>,
    ) : CreateLoanEvent

    sealed interface Ui : BaseEvent.Ui, CreateLoanEvent {

        data class AmountTextValueChange(val value: TextFieldValue) : Ui

        data object CreateLoanButtonClick : Ui

        data class TimeTextValueChange(val value: TextFieldValue) : Ui

        data class ChoosePaymentAccount(val account: Account) : Ui
    }
}