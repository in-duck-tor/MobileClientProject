package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Target
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.GetAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.MakeTransactionUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model.TransactionEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model.TransactionEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model.TransactionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(
    getUserLoginUseCase: GetUserLoginUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val observeAccountsUseCase: ObserveAccountsUseCase,
    private val makeTransactionUseCase: MakeTransactionUseCase,
) : BaseViewModel<TransactionState, TransactionEvent, TransactionEffect>() {

    override fun initState(): TransactionState = TransactionState.Loading

    private val login = getUserLoginUseCase()

    private lateinit var baseAccounts: List<Account>

    override fun processEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.Init -> handleInit(event)
            is TransactionEvent.DataLoaded -> handleDataLoaded(event)
            is TransactionEvent.Ui.AmountValueChange -> handleAmountChange(event)
            is TransactionEvent.Ui.WithdrawAccountChoice -> handleWithdrawAccountChoice(event)
            is TransactionEvent.Ui.DepositButtonClick -> handleDepositButtonClick(event)
            is TransactionEvent.Ui.WithdrawButtonClick -> handleWithdrawButtonClick(event)
        }
    }

    private fun handleInit(event: TransactionEvent.Init) {
        viewModelScope.launch(Dispatchers.IO) {
            getAccountUseCase(event.accountNumber)
                .onSuccess { depositAccount ->
                    observeAccounts(depositAccount)
                }
                .onFailure {
                    Log.e(TAG, it.message.toString())
                    addEffect(
                        TransactionEffect.ShowError(
                            "Error while getting account data: ${it.message}"
                        )
                    )
                }
        }
    }

    private suspend fun observeAccounts(depositAccount: Account) {
        observeAccountsUseCase(login)
            .onSuccess { flow ->
                flow.collect { accountsList ->
                    val correctList = accountsList.filter { it.number != depositAccount.number }
                    baseAccounts = correctList
                    processEvent(
                        TransactionEvent.DataLoaded(
                            depositAccount = depositAccount,
                            accounts = correctList
                        )
                    )
                }
            }
            .onFailure {
                Log.e(TAG, it.message.toString())
                addEffect(
                    TransactionEffect.ShowError(
                        "Error while observing accounts: ${it.message}"
                    )
                )
            }
    }

    private fun handleDataLoaded(event: TransactionEvent.DataLoaded) {
        when (val currentState = state.value) {
            is TransactionState.Content -> updateState {
                currentState.copy(
                    accounts = event.accounts,
                    defaultAccount = event.depositAccount
                )
            }

            is TransactionState.Loading -> updateState {
                TransactionState.Content(
                    accounts = event.accounts,
                    defaultAccount = event.depositAccount,
                    chosenAccount = event.accounts.first()
                )
            }
        }
    }

    private fun handleAmountChange(event: TransactionEvent.Ui.AmountValueChange) {
        when (val currentState = state.value) {
            is TransactionState.Content -> {

                val amount = try {
                    event.amountText.text.toDouble()
                } catch (e: Exception) {
                    0.0
                }

                updateState {
                    currentState.copy(
                        amount = amount,
                        amountText = event.amountText
                    )
                }
            }

            else -> Unit
        }
    }

    private fun handleWithdrawAccountChoice(event: TransactionEvent.Ui.WithdrawAccountChoice) {
        when (val currentState = state.value) {
            is TransactionState.Content -> {
                if (currentState.amount > currentState.chosenAccount.amount) {
                    addEffect(TransactionEffect.ShowError("AMOUNT MUST BE SMALLER"))
                }
                updateState { currentState.copy(chosenAccount = event.account) }
            }

            else -> Unit
        }
    }

    private fun handleDepositButtonClick(event: TransactionEvent.Ui.DepositButtonClick) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentState = state.value) {
                is TransactionState.Content -> {
                    makeTransactionUseCase(setupDepositData(currentState))
                        .onSuccess {
                            addEffect(
                                TransactionEffect.ShowCreationToast("TRANSACTION MADE")
                            )
                            addEffect(TransactionEffect.CloseSelf)
                        }
                        .onFailure {
                            Log.e(TAG, it.message.toString())
                            addEffect(
                                TransactionEffect.ShowError("ERROR DURING MAKING TRANSACTION: ${it.message}")
                            )
                        }
                }

                else -> Unit
            }

        }
    }

    private fun setupDepositData(
        state: TransactionState.Content,
    ): TransactionRequest {
        return TransactionRequest(
            amount = state.amount,
            depositOn = Target(
                accountNumber = state.defaultAccount.number,
            ),
            withdrawFrom = null,
        )
    }

    private fun handleWithdrawButtonClick(event: TransactionEvent.Ui.WithdrawButtonClick) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentState = state.value) {
                is TransactionState.Content -> {
                    val data = setupWithdrawData(currentState, event.isSelf)
                    if (data.amount > currentState.defaultAccount.amount) {
                        addEffect(TransactionEffect.ShowError("AMOUNT MUST BE SMALLER"))
                    } else {
                        makeTransactionUseCase(data)
                            .onSuccess {
                                addEffect(
                                    TransactionEffect.ShowCreationToast("TRANSACTION MADE")
                                )
                                addEffect(TransactionEffect.CloseSelf)
                            }
                            .onFailure {
                                Log.e(TAG, it.message.toString())
                                addEffect(
                                    TransactionEffect.ShowError("ERROR DURING MAKING TRANSACTION: ${it.message}")
                                )
                            }
                    }
                }

                else -> Unit
            }

        }
    }

    private fun setupWithdrawData(
        state: TransactionState.Content,
        isSelf: Boolean,
    ): TransactionRequest {
        return TransactionRequest(
            amount = state.amount,
            withdrawFrom = Target(
                accountNumber = state.defaultAccount.number,
            ),
            depositOn = if (isSelf) null else Target(
                accountNumber = state.chosenAccount.number,
            ),
        )
    }

    private companion object {

        const val TAG = "TransactionViewModel"
    }
}