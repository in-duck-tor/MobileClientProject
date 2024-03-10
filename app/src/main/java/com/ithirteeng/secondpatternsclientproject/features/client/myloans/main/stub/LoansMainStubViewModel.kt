package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.stub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansStubDatasource
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model.MyLoansMainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoansMainStubViewModel(
    getLocalTokenUseCase: GetLocalTokenUseCase,
    private val loansStubDatasource: LoansStubDatasource,
) : ViewModel() {

    private val token = getLocalTokenUseCase()
    val state = MutableStateFlow(MyLoansMainState(listOf(), true))

    fun observeLoans() {
        viewModelScope.launch(Dispatchers.IO) {
            loansStubDatasource.observeLoans(token).collect {
                state.value = state.value.copy(
                    loans = it,
                    isLoading = false
                )
            }
        }
    }
}