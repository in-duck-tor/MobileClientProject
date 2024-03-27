package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.stub

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Target
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.GetAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub.MakeTransactionBetweenAccountsStubUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub.ReplenishAccountStubUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub.WithdrawFromAccountStubUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionStubViewModel(
    getUserLoginUseCase: GetUserLoginUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val observeAccountsUseCase: ObserveAccountsUseCase,
    private val makeTransactionBetweenAccountsStubUseCase: MakeTransactionBetweenAccountsStubUseCase,
    private val replenishAccountStubUseCase: ReplenishAccountStubUseCase,
    private val withdrawFromAccountStubUseCase: WithdrawFromAccountStubUseCase,
) : BaseViewModel<SelfTransactionState, StubTransactionEvent, SelfTransactionEffect>() {

    override fun initState(): SelfTransactionState = SelfTransactionState.Loading

    private val login = getUserLoginUseCase()

    private lateinit var baseAccounts: List<Account>

    override fun processEvent(event: StubTransactionEvent) {
        when (event) {
            is StubTransactionEvent.Init -> handleInit(event)
            is StubTransactionEvent.DataLoaded -> handleDataLoaded(event)
            is StubTransactionEvent.Ui.AmountValueChange -> handleAmountChange(event)
            is StubTransactionEvent.Ui.WithdrawAccountChoice -> handleWithdrawAccountChoice(event)
            is StubTransactionEvent.Ui.DepositButtonClick -> handleDepositButtonClick()
            is StubTransactionEvent.Ui.WithdrawButtonClick -> handleWithdrawButtonClick()
            StubTransactionEvent.Ui.TransferButtonClick -> handleTransferButtonClick()
        }
    }

    private fun handleInit(event: StubTransactionEvent.Init) {
        viewModelScope.launch(Dispatchers.IO) {
            getAccountUseCase(event.accountNumber)
                .onSuccess { depositAccount ->
                    observeAccounts(depositAccount)
                }
                .onFailure {
                    Log.e(TAG, it.message.toString())
                    addEffect(
                        SelfTransactionEffect.ShowError(
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
                        StubTransactionEvent.DataLoaded(
                            depositAccount = depositAccount,
                            accounts = correctList
                        )
                    )
                }
            }
            .onFailure {
                Log.e(TAG, it.message.toString())
                addEffect(
                    SelfTransactionEffect.ShowError(
                        "Error while observing accounts: ${it.message}"
                    )
                )
            }
    }

    private fun handleDataLoaded(event: StubTransactionEvent.DataLoaded) {
        when (val currentState = state.value) {
            is SelfTransactionState.Content -> updateState {
                currentState.copy(
                    accounts = event.accounts,
                    defaultAccount = event.depositAccount
                )
            }

            is SelfTransactionState.Loading -> updateState {
                SelfTransactionState.Content(
                    accounts = event.accounts,
                    defaultAccount = event.depositAccount,
                    chosenAccount = event.accounts.first(),
                    currencyRates = listOf()
                )
            }
        }
    }

    private fun handleAmountChange(event: StubTransactionEvent.Ui.AmountValueChange) {
        when (val currentState = state.value) {
            is SelfTransactionState.Content -> {
                if (event.amountText.text.toDouble() > currentState.chosenAccount.amount) {
                    addEffect(SelfTransactionEffect.ShowError("AMOUNT MUST BE SMALLER"))
                } else {
                    updateState {
                        currentState.copy(
                            amount = event.amountText.text.toDouble(),
                            amountText = event.amountText
                        )
                    }
                }
            }

            else -> Unit
        }
    }

    private fun handleWithdrawAccountChoice(event: StubTransactionEvent.Ui.WithdrawAccountChoice) {
        when (val currentState = state.value) {
            is SelfTransactionState.Content -> {
                if (currentState.amount > currentState.chosenAccount.amount) {
                    addEffect(SelfTransactionEffect.ShowError("AMOUNT MUST BE SMALLER"))
                }
                updateState { currentState.copy(chosenAccount = event.account) }
            }

            else -> Unit
        }
    }

    private fun handleDepositButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentState = state.value) {
                is SelfTransactionState.Content -> {
                    replenishAccountStubUseCase(
                        amount = currentState.amount,
                        accountNumber = currentState.defaultAccount.number,
                        token = login
                    )
                        .onSuccess {
                            addEffect(
                                SelfTransactionEffect.ShowCreationToast("TRANSACTION MADE")
                            )
                            addEffect(SelfTransactionEffect.CloseSelf)
                        }
                        .onFailure {
                            Log.e(TAG, it.message.toString())
                            addEffect(
                                SelfTransactionEffect.ShowError("ERROR DURING MAKING TRANSACTION: ${it.message}")
                            )
                        }

                }

                else -> Unit
            }

        }
    }

    private fun handleWithdrawButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentState = state.value) {
                is SelfTransactionState.Content -> {
                    if (currentState.amount > currentState.defaultAccount.amount) {
                        addEffect(SelfTransactionEffect.ShowError("AMOUNT MUST BE SMALLER"))
                    } else {
                        withdrawFromAccountStubUseCase(
                            amount = currentState.amount,
                            accountNumber = currentState.defaultAccount.number,
                            token = login
                        )
                            .onSuccess {
                                addEffect(
                                    SelfTransactionEffect.ShowCreationToast("TRANSACTION MADE")
                                )
                                addEffect(SelfTransactionEffect.CloseSelf)
                            }
                            .onFailure {
                                Log.e(TAG, it.message.toString())
                                addEffect(
                                    SelfTransactionEffect.ShowError("ERROR DURING MAKING TRANSACTION: ${it.message}")
                                )
                            }
                    }
                }

                else -> Unit
            }

        }
    }

    private fun handleTransferButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentState = state.value) {
                is SelfTransactionState.Content -> {
                    if (currentState.amount > currentState.defaultAccount.amount) {
                        addEffect(SelfTransactionEffect.ShowError("AMOUNT MUST BE SMALLER"))
                    } else {
                        makeTransactionBetweenAccountsStubUseCase(
                            setupWithdrawData(currentState),
                            login
                        )
                            .onSuccess {
                                addEffect(
                                    SelfTransactionEffect.ShowCreationToast("TRANSACTION MADE")
                                )
                                addEffect(SelfTransactionEffect.CloseSelf)
                            }
                            .onFailure {
                                Log.e(TAG, it.message.toString())
                                addEffect(
                                    SelfTransactionEffect.ShowError("ERROR DURING MAKING TRANSACTION: ${it.message}")
                                )
                            }
                    }
                }

                else -> Unit
            }

        }
    }

    private fun setupWithdrawData(state: SelfTransactionState.Content): TransactionRequest {
        return TransactionRequest(
            amount = state.amount,
            withdrawFrom = Target(
                accountNumber = state.defaultAccount.number,
            ),
            depositOn = Target(
                accountNumber = state.chosenAccount.number,
            ),
        )
    }

    private companion object {

        const val TAG = "TransactionViewModel"
    }
}