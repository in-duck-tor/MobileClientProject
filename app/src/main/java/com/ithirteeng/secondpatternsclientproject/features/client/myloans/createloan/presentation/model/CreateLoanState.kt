package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse

sealed interface CreateLoanState : BaseState {

    data object Loading : CreateLoanState

    data class Content(
        val program: LoanProgramResponse,
        val amount: Double = 0.0,
        val amountText: TextFieldValue = TextFieldValue(amount.toString()),
        val accounts: List<Account>,
        val chosenAccount: Account,
        val timeInMonths: Int = 0,
        val timeText: TextFieldValue = TextFieldValue(timeInMonths.toString())
    ) : CreateLoanState
}