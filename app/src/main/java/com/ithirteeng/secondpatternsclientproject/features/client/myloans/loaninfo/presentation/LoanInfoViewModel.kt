package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansStubDatasource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoanInfoViewModel(
    private val datasource: LoansStubDatasource,
) : ViewModel() {

    val state = MutableStateFlow(LoanInfoState())

    fun getAccount(loanId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val loan = datasource.getLoan(loanId)
            state.value = state.value.copy(
                loan = loan,
                isLoading = false
            )
        }
    }

    fun deleteAccount(loanId: String) {
        state.value = state.value.copy(isLoading = true, isDeletedButtonClick = true)
        viewModelScope.launch(Dispatchers.IO) {
            datasource.deleteLoan(loanId)
            state.value = state.value.copy(isDeleted = true, isLoading = false)
        }
    }
}