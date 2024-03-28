package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetLoanProgramsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetUserLoansUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserIdUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model.MyLoansMainEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model.MyLoansMainEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.model.MyLoansMainState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyLoansMainViewModel(
    getUserIdUseCase: GetUserIdUseCase,
    private val getUserLoansUseCase: GetUserLoansUseCase,
    private val getLoanProgramsUseCase: GetLoanProgramsUseCase,
) : BaseViewModel<MyLoansMainState, MyLoansMainEvent, MyLoansMainEffect>() {

    private val userId = getUserIdUseCase()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.message.toString())
        addEffect(MyLoansMainEffect.ShowError(throwable.message ?: "SOME ERROR"))
    }

    override fun initState(): MyLoansMainState = MyLoansMainState.Loading

    override fun processEvent(event: MyLoansMainEvent) {
        when (event) {
            is MyLoansMainEvent.Init -> handleInit()
            is MyLoansMainEvent.DataLoaded -> handleDataLoaded(event)
            is MyLoansMainEvent.Ui.LoanClick -> handleLoanClick(event)
            is MyLoansMainEvent.Ui.LoanProgramClick -> handleProgramClick(event)
        }
    }

    private fun handleInit() {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val loansDeferred = async { getUserLoansUseCase(userId) }
            val programsDeferred = async { getLoanProgramsUseCase() }

            processEvent(
                MyLoansMainEvent.DataLoaded(
                    loans = loansDeferred.await(),
                    programs = programsDeferred.await()
                )
            )
        }
    }

    private fun handleDataLoaded(event: MyLoansMainEvent.DataLoaded) {
        when (val currentState = state.value) {
            is MyLoansMainState.Content -> updateState {
                currentState.copy(
                    programs = event.programs,
                    loans = event.loans,
                )
            }

            is MyLoansMainState.Loading -> updateState {
                MyLoansMainState.Content(
                    programs = event.programs,
                    loans = event.loans
                )
            }
        }
    }

    private fun handleLoanClick(event: MyLoansMainEvent.Ui.LoanClick) {
        addEffect(MyLoansMainEffect.NavigateToLoanInfoScreen(event.loanId))
    }

    private fun handleProgramClick(event: MyLoansMainEvent.Ui.LoanProgramClick) {
        addEffect(MyLoansMainEffect.NavigateToProgramInfoScreen(event.programId))
    }

    private companion object {
        private const val TAG = "MyLoansMainViewModel"
    }
}