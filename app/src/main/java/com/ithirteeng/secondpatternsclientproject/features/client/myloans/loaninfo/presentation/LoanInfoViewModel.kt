package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetLoanInfoUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.MakePaymentToLoanUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserIdUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoanInfoViewModel(
    private val getLoanInfoUseCase: GetLoanInfoUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val makePaymentToLoanUseCase: MakePaymentToLoanUseCase,
    private val observeAccountsUseCase: ObserveAccountsUseCase,
) : BaseViewModel<LoanInfoState, LoanInfoEvent, LoanInfoEffect>() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.message.toString())
        addEffect(LoanInfoEffect.ShowError(throwable.message ?: "SOME ERROR"))
    }

    override fun initState(): LoanInfoState = LoanInfoState.Loading

    override fun processEvent(event: LoanInfoEvent) {
        when (event) {
            is LoanInfoEvent.Init -> handleInit(event)
            is LoanInfoEvent.DataLoaded -> handleDataLoaded(event)
            is LoanInfoEvent.Ui.MakePaymentButtonClick -> TODO()
        }
    }

    private fun handleInit(event: LoanInfoEvent.Init) {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val loanInfo = getLoanInfoUseCase(event.loanId)
            processEvent(LoanInfoEvent.DataLoaded(loanInfo = loanInfo))
        }
    }

    private fun handleDataLoaded(event: LoanInfoEvent.DataLoaded) {
        when (val currentState = state.value) {
            is LoanInfoState.Content -> updateState {
                currentState.copy(loanInfo = event.loanInfo)
            }

            is LoanInfoState.Loading -> updateState {
                LoanInfoState.Content(
                    loanInfo = event.loanInfo
                )
            }
        }
    }

    private companion object {
        private const val TAG = "LoanInfo"
    }

}