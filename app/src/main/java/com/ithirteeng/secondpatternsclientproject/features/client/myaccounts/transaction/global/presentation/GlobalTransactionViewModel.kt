package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Target
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.GetBanksInfoUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.GetAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.MakeTransactionUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.model.GlobalTransactionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlobalTransactionViewModel(
    private val getAccountUseCase: GetAccountUseCase,
    private val makeTransactionUseCase: MakeTransactionUseCase,
    private val getBanksInfoUseCase: GetBanksInfoUseCase,
) : BaseViewModel<GlobalTransactionState, GlobalTransactionEvent, GlobalTransactionEffect>() {

    override fun initState(): GlobalTransactionState = GlobalTransactionState.Loading

    override fun processEvent(event: GlobalTransactionEvent) {
        when (event) {
            is GlobalTransactionEvent.Init -> handleInit(event)

            is GlobalTransactionEvent.Ui.AccountNumberChange -> handleAccountNumberChange(event)
            is GlobalTransactionEvent.Ui.AmountValueChange -> handleAmountChange(event)
            is GlobalTransactionEvent.Ui.MakeTransactionButtonClick -> handleMakeTransactionButtonClick()
            is GlobalTransactionEvent.Ui.BankChoice -> handleBankChoice(event)
        }
    }

    private fun handleInit(event: GlobalTransactionEvent.Init) {
        viewModelScope.launch(Dispatchers.IO) {
            getAccountUseCase(event.accountId)
                .onSuccess { account ->
                    getBanksInfoUseCase()
                        .onSuccess { banks ->
                            when (val currentState = state.value) {
                                is GlobalTransactionState.Loading -> updateState {
                                    GlobalTransactionState.Content(
                                        account = account,
                                        banks = banks,
                                        chosenBank = banks.first()
                                    )
                                }

                                is GlobalTransactionState.Content -> updateState {
                                    currentState.copy(account = account, banks = banks)
                                }
                            }
                        }
                        .onFailure {
                            addEffect(
                                GlobalTransactionEffect.ShowError(
                                    it.message ?: "banks error"
                                )
                            )
                        }
                }
                .onFailure {
                    addEffect(
                        GlobalTransactionEffect.ShowError(
                            it.message ?: "account trouble use case"
                        )
                    )
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

    private fun handleBankChoice(event: GlobalTransactionEvent.Ui.BankChoice) {
        when (val currentState = state.value) {
            is GlobalTransactionState.Content -> updateState {
                currentState.copy(chosenBank = event.bank)
            }

            is GlobalTransactionState.Loading -> Unit
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
            is GlobalTransactionState.Content -> {
                if (currentState.amount > currentState.account.amount) {
                    addEffect(GlobalTransactionEffect.ShowError("AMOUNT must be smaller"))
                } else if (currentState.accountNumber.text.isEmpty()) {
                    addEffect(GlobalTransactionEffect.ShowError("account number mustn\'t be empty"))
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        makeTransactionUseCase(
                            transactionRequest = TransactionRequest(
                                amount = currentState.amount,
                                depositOn = Target(
                                    accountNumber = currentState.accountNumber.text,
                                    bankCode = currentState.chosenBank.bankCode.toString()
                                ),
                                withdrawFrom = Target(
                                    accountNumber = currentState.account.number,
                                )
                            )
                        )
                            .onSuccess {
                                addEffect(
                                    GlobalTransactionEffect.ShowError("Transaction Made")
                                )
                                addEffect(GlobalTransactionEffect.CloseSelf)
                            }
                            .onFailure {
                                addEffect(
                                    GlobalTransactionEffect.ShowError(
                                        it.message ?: "Error during transaction"
                                    )
                                )
                            }
                    }
                }
            }
        }
    }
}