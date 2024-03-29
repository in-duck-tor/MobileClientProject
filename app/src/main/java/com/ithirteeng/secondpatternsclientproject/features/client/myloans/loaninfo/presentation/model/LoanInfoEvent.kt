package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoResponse

sealed interface LoanInfoEvent : BaseEvent {

    data class Init(val loanId: Long) : LoanInfoEvent

    data class DataLoaded(
        val loanInfo: LoanInfoResponse,
        val accounts: List<Account>
    ) : LoanInfoEvent

    sealed interface Ui : BaseEvent.Ui, LoanInfoEvent {

        data object MakePaymentButtonClick : Ui

        data class AmountTextValueChange(val amount: TextFieldValue) : Ui

        data class ChoosePaymentAccount(val account: Account) : Ui
    }
}