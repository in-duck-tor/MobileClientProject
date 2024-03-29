package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.loan.ApplicationInfo
import com.ithirteeng.secondpatternsclientproject.domain.loans.model.program.LoanProgramResponse
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.GetLoanProgramsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.loans.usecase.SubmitLoanApplicationUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserIdUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model.CreateLoanEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model.CreateLoanEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.model.CreateLoanState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateLoanViewModel(
    getUserIdUseCase: GetUserIdUseCase,
    getUserLoginUseCase: GetUserLoginUseCase,
    private val observeAccountsUseCase: ObserveAccountsUseCase,
    private val getLoanProgramsUseCase: GetLoanProgramsUseCase,
    private val submitLoanApplicationUseCase: SubmitLoanApplicationUseCase,
) : BaseViewModel<CreateLoanState, CreateLoanEvent, CreateLoanEffect>() {

    private val login = getUserLoginUseCase()
    private val userId = getUserIdUseCase()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, throwable.message.toString())
        addEffect(CreateLoanEffect.ShowError(throwable.message ?: "SOME ERROR"))
    }

    override fun initState(): CreateLoanState = CreateLoanState.Loading

    override fun processEvent(event: CreateLoanEvent) {
        when (event) {
            is CreateLoanEvent.Init -> handleInit(event)
            is CreateLoanEvent.DataLoaded -> handleDataLoaded(event)

            is CreateLoanEvent.Ui.AmountTextValueChange -> handleAmountChange(event)
            is CreateLoanEvent.Ui.ChoosePaymentAccount -> handleAccountChoice(event)
            is CreateLoanEvent.Ui.CreateLoanButtonClick -> handleCreateLoanButtonClick()
            is CreateLoanEvent.Ui.TimeTextValueChange -> handleTimeValueChange(event)
        }
    }

    private fun handleInit(event: CreateLoanEvent.Init) {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val program = getLoanProgramsUseCase().findLast { it.id == event.programId }
            program?.let {
                observeAccounts(program)
            }
        }
    }

    private suspend fun observeAccounts(program: LoanProgramResponse) {
        observeAccountsUseCase(login)
            .onSuccess { flow ->
                flow.collect { accountsList ->
                    val correctList = accountsList
                        .filter { it.state == AccountState.active }
                    processEvent(
                        CreateLoanEvent.DataLoaded(
                            program = program,
                            accounts = correctList
                        )
                    )
                }
            }
            .onFailure {
                Log.e(TAG, it.message.toString())
                addEffect(
                    CreateLoanEffect.ShowError(
                        "Error while observing accounts: ${it.message}"
                    )
                )
            }
    }

    private fun handleDataLoaded(event: CreateLoanEvent.DataLoaded) {
        when (val currentState = state.value) {
            is CreateLoanState.Content -> updateState {
                currentState.copy(accounts = event.accounts, program = event.program)
            }

            is CreateLoanState.Loading -> updateState {
                CreateLoanState.Content(
                    program = event.program,
                    accounts = event.accounts,
                    chosenAccount = event.accounts.first(),
                )
            }
        }
    }

    private fun handleAmountChange(event: CreateLoanEvent.Ui.AmountTextValueChange) {
        when (val currentState = state.value) {
            is CreateLoanState.Content -> {
                val amount = try {
                    event.value.text.toDouble()
                } catch (e: Exception) {
                    0.0
                }
                updateState {
                    currentState.copy(
                        amount = if (amount <= 0.0) currentState.amount else amount,
                        amountText = if (amount <= 0.0) currentState.amountText else event.value
                    )
                }
            }

            else -> Unit
        }
    }

    private fun handleTimeValueChange(event: CreateLoanEvent.Ui.TimeTextValueChange) {
        when (val currentState = state.value) {
            is CreateLoanState.Content -> {
                val time = try {
                    event.value.text.toInt()
                } catch (e: Exception) {
                    0
                }
                if (time <= 0 || time > 100) {
                    addEffect(CreateLoanEffect.ShowError("You can create loan on less then 100 months"))
                } else {
                    updateState {
                        currentState.copy(
                            timeInMonths = time,
                            timeText = event.value
                        )
                    }
                }
            }

            else -> Unit
        }
    }

    private fun handleAccountChoice(event: CreateLoanEvent.Ui.ChoosePaymentAccount) {
        when (val currentState = state.value) {
            is CreateLoanState.Content -> {
                updateState { currentState.copy(chosenAccount = event.account) }
            }

            else -> Unit
        }
    }

    private fun handleCreateLoanButtonClick() {
        when (val currentState = state.value) {
            is CreateLoanState.Content -> {
                viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
                    submitLoanApplicationUseCase(
                        applicationInfo = ApplicationInfo(
                            clientId = userId.toLong(),
                            borrowedAmount = currentState.amount,
                            loanProgramId = currentState.program.id,
                            loanTerm = currentState.timeInMonths.toLong() * 30 * 24 * 60 * 60,
                            clientAccountNumber = currentState.chosenAccount.number
                        )
                    ).onSuccess {
                        addEffect(CreateLoanEffect.ShowError("Application submitted"))
                        addEffect(CreateLoanEffect.CloseSelf)
                    }
                }
            }

            else -> Unit
        }
    }

    private companion object {
        private const val TAG = "CreateLoan"
    }
}
