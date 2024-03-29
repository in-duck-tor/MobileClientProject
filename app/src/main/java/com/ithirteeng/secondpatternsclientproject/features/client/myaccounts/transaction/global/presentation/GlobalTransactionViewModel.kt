package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.GetAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.MakeTransactionUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlobalTransactionViewModel(
    getUserLoginUseCase: GetUserLoginUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val makeTransactionUseCase: MakeTransactionUseCase,
) : BaseViewModel<GlobalTransactionState, GlobalTransactionEvent, GlobalTransactionEffect>() {

    private val login = getUserLoginUseCase()

    override fun initState(): GlobalTransactionState = GlobalTransactionState.Loading

    override fun processEvent(event: GlobalTransactionEvent) {
        when (event) {
            is GlobalTransactionEvent.Init -> handleInit(event)

            is GlobalTransactionEvent.Ui.AccountNumberChange -> handleAccountNumberChange(event)
            is GlobalTransactionEvent.Ui.AmountValueChange -> handleAmountChange(event)
            is GlobalTransactionEvent.Ui.MakeTransactionButtonClick -> TODO()
        }
    }

    private fun handleInit(event: GlobalTransactionEvent.Init) {
        viewModelScope.launch(Dispatchers.IO) {
            getAccountUseCase(event.accountId)
                .onSuccess { account ->
                    when (val currentState = state.value) {
                        is GlobalTransactionState.Loading -> updateState {
                            GlobalTransactionState.Content(account = account)
                        }

                        is GlobalTransactionState.Content -> updateState {
                            currentState.copy(account = account)
                        }
                    }
                }
                .onFailure {
                    addEffect(GlobalTransactionEffect.ShowError(it.message ?: "BULLSHIT"))
                }
        }
    }

    private fun handleAmountChange(event: GlobalTransactionEvent.Ui.AmountValueChange) {
        when (val currentState = state.value) {
            is GlobalTransactionState.Content -> {

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

    private fun handleAccountNumberChange(event: GlobalTransactionEvent.Ui.AccountNumberChange) {
        when (val currentState = state.value) {
            is GlobalTransactionState.Loading -> Unit
            is GlobalTransactionState.Content -> {
                if (Regex("[0-9]*").matches(event.value.text)) {
                    updateState {
                        currentState.copy(accountNumber = event.value)
                    }
                }
            }
        }
    }

    private fun handleMakeTransactionButtonClick() {
        when (val currentState = state.value) {
            is GlobalTransactionState.Loading -> Unit
            is GlobalTransactionState.Content -> Unit
        }
    }


}