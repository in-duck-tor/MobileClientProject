package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.LoanInfoResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.payment.EarlyPayoffBody
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetLoanInfoUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.MakePaymentToLoanUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserIdUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.model.LoanInfoState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoanInfoViewModel(
    getUserIdUseCase: GetUserIdUseCase,
    getUserLoginUseCase: GetUserLoginUseCase,
    private val getLoanInfoUseCase: GetLoanInfoUseCase,
    private val makePaymentToLoanUseCase: MakePaymentToLoanUseCase,
    private val observeAccountsUseCase: ObserveAccountsUseCase,
) : BaseViewModel<LoanInfoState, LoanInfoEvent, LoanInfoEffect>() {

    private val userId = getUserIdUseCase()
    private val login = getUserLoginUseCase()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.message.toString())
        addEffect(LoanInfoEffect.ShowError(throwable.message ?: "SOME ERROR"))
    }

    override fun initState(): LoanInfoState = LoanInfoState.Loading

    override fun processEvent(event: LoanInfoEvent) {
        when (event) {
            is LoanInfoEvent.Init -> handleInit(event)
            is LoanInfoEvent.DataLoaded -> handleDataLoaded(event)
            is LoanInfoEvent.Ui.MakePaymentButtonClick -> handleMakePaymentButtonClick()
            is LoanInfoEvent.Ui.AmountTextValueChange -> handleAmountTextValueChange(event)
            is LoanInfoEvent.Ui.ChoosePaymentAccount -> handlePaymentAccountChoice(event)
        }
    }

    private fun handleInit(event: LoanInfoEvent.Init) {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val loanInfo = getLoanInfoUseCase(event.loanId)
            observeAccounts(loanInfo)
        }
    }

    private suspend fun observeAccounts(loanInfo: LoanInfoResponse) {
        observeAccountsUseCase(login)
            .onSuccess { flow ->
                flow.collect { accountsList ->
                    processEvent(
                        LoanInfoEvent.DataLoaded(
                            loanInfo = loanInfo,
                            accounts = accountsList.filter { it.state == AccountState.active }
                        )
                    )
                }
            }
            .onFailure {
                Log.e(TAG, it.message.toString())
                addEffect(
                    LoanInfoEffect.ShowError("Error while observing accounts: ${it.message}")
                )
            }
    }

    private fun handleDataLoaded(event: LoanInfoEvent.DataLoaded) {
        when (val currentState = state.value) {
            is LoanInfoState.Content -> updateState {
                currentState.copy(loanInfo = event.loanInfo)
            }

            is LoanInfoState.Loading -> updateState {
                LoanInfoState.Content(
                    loanInfo = event.loanInfo,
                    accounts = event.accounts,
                    chosenAccount = event.accounts.first(),
                )
            }
        }
    }

    private fun handleMakePaymentButtonClick() {
        when (val currentState = state.value) {
            is LoanInfoState.Loading -> Unit
            is LoanInfoState.Content -> {
                if (currentState.amount > currentState.chosenAccount.amount || currentState.amount < 0) {
                    addEffect(LoanInfoEffect.ShowError("amount must be in rage 0..${currentState.chosenAccount.amount}"))
                } else {
                    viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
                        makePaymentToLoanUseCase(
                            loanId = currentState.loanInfo.id.toString(),
                            payoffBody = EarlyPayoffBody(
                                clientId = userId,
                                payment = currentState.amount
                            )
                        )
                    }
                }
            }
        }
    }

    private fun handleAmountTextValueChange(event: LoanInfoEvent.Ui.AmountTextValueChange) {
        when (val currentState = state.value) {
            is LoanInfoState.Loading -> Unit
            is LoanInfoState.Content -> {
                try {
                    val value = event.amount.text.toDouble()
                    updateState {
                        currentState.copy(
                            amount = value,
                            amountText = event.amount
                        )
                    }
                } catch (e: Exception) {
                    addEffect(LoanInfoEffect.ShowError("AMOUNT mut be numeric"))
                }
            }
        }
    }

    private fun handlePaymentAccountChoice(event: LoanInfoEvent.Ui.ChoosePaymentAccount) {
        when (val currentState = state.value) {
            is LoanInfoState.Loading -> Unit
            is LoanInfoState.Content -> updateState {
                currentState.copy(chosenAccount = event.account)
            }
        }
    }

    private companion object {
        private const val TAG = "LoanInfo"
    }

}