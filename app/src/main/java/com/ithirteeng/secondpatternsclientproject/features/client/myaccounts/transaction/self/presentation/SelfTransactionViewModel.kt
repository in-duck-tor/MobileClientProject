package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.Target
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.transaction.TransactionRequest
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.GetAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.MakeTransactionUseCase
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.Currency
import com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase.ConvertCurrencyByCharCodeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase.GetCurrencyRatesUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionAction
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.model.SelfTransactionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelfTransactionViewModel(
    getUserLoginUseCase: GetUserLoginUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val observeAccountsUseCase: ObserveAccountsUseCase,
    private val makeTransactionUseCase: MakeTransactionUseCase,
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase,
    private val convertCurrencyByCharCodeUseCase: ConvertCurrencyByCharCodeUseCase,
) : BaseViewModel<SelfTransactionState, SelfTransactionEvent, SelfTransactionEffect>() {

    override fun initState(): SelfTransactionState = SelfTransactionState.Loading

    private val login = getUserLoginUseCase()

    private lateinit var baseAccounts: List<Account>

    override fun processEvent(event: SelfTransactionEvent) {
        when (event) {
            is SelfTransactionEvent.Init -> handleInit(event)
            is SelfTransactionEvent.DataLoaded -> handleDataLoaded(event)
            is SelfTransactionEvent.Ui.AmountValueChange -> handleAmountChange(event)
            is SelfTransactionEvent.Ui.WithdrawAccountChoice -> handleWithdrawAccountChoice(event)
            is SelfTransactionEvent.Ui.DepositButtonClick -> handleDepositButtonClick()
            is SelfTransactionEvent.Ui.WithdrawButtonClick -> handleWithdrawButtonClick(event)
            is SelfTransactionEvent.Ui.CloseMoneyInfoDialog -> handleMoneyInfoDialogClose()
        }
    }

    private fun handleInit(event: SelfTransactionEvent.Init) {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrencyRatesUseCase()
                .onSuccess { rates ->
                    getAccountUseCase(event.accountNumber)
                        .onSuccess { depositAccount ->
                            observeAccounts(depositAccount, rates)
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
                .onFailure {
                    Log.e(TAG, it.message.toString())
                    addEffect(
                        SelfTransactionEffect.ShowError(
                            "Error while getting currency rates: ${it.message}"
                        )
                    )
                }
        }
    }

    private suspend fun observeAccounts(depositAccount: Account, rates: List<Currency>) {
        observeAccountsUseCase(login)
            .onSuccess { flow ->
                flow.collect { accountsList ->
                    val correctList = accountsList.filter { it.number != depositAccount.number }
                    baseAccounts = correctList
                    processEvent(
                        SelfTransactionEvent.DataLoaded(
                            depositAccount = depositAccount,
                            accounts = correctList,
                            rates = rates
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

    private fun handleDataLoaded(event: SelfTransactionEvent.DataLoaded) {
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
                    currencyRates = event.rates
                )
            }
        }
    }

    private fun handleAmountChange(event: SelfTransactionEvent.Ui.AmountValueChange) {
        when (val currentState = state.value) {
            is SelfTransactionState.Content -> {

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

    private fun handleWithdrawAccountChoice(event: SelfTransactionEvent.Ui.WithdrawAccountChoice) {
        when (val currentState = state.value) {
            is SelfTransactionState.Content -> {
                updateState { currentState.copy(chosenAccount = event.account) }
            }

            else -> Unit
        }
    }

    private fun handleMoneyInfoDialogClose() {
        when (val currentState = state.value) {
            is SelfTransactionState.Loading -> Unit
            is SelfTransactionState.Content -> {
                when (currentState.finishAction) {
                    SelfTransactionAction.DEPOSIT_SELF -> {
                        deposit()
                    }

                    SelfTransactionAction.WITHDRAW_SELF -> {
                        withdraw(true)
                    }

                    SelfTransactionAction.WITHDRAW -> {
                        withdraw(false)
                    }

                    null -> Unit
                }
            }
        }
    }

    private fun handleDepositButtonClick() {
        when (val currentState = state.value) {
            is SelfTransactionState.Content -> {
                openMoneyInfoDialog(currentState, SelfTransactionAction.DEPOSIT_SELF)
            }

            is SelfTransactionState.Loading -> Unit
        }
    }

    private fun deposit() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentState = state.value) {
                is SelfTransactionState.Content -> {
                    makeTransactionUseCase(setupDepositData(currentState))
                        .onSuccess {
                            updateState {
                                currentState.copy(
                                    isTransactionInfoDialogOpen = false
                                )
                            }
                            addEffect(SelfTransactionEffect.ShowCreationToast("TRANSACTION MADE"))
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

    private fun setupDepositData(
        state: SelfTransactionState.Content,
    ): TransactionRequest {
        return TransactionRequest(
            amount = state.amount,
            depositOn = Target(
                accountNumber = state.defaultAccount.number,
            ),
            withdrawFrom = null,
        )
    }

    private fun handleWithdrawButtonClick(event: SelfTransactionEvent.Ui.WithdrawButtonClick) {
        when (val currentState = state.value) {
            is SelfTransactionState.Content -> {
                val action =
                    if (event.isSelf) SelfTransactionAction.WITHDRAW_SELF else SelfTransactionAction.WITHDRAW
                openMoneyInfoDialog(currentState, action)
            }

            is SelfTransactionState.Loading -> Unit
        }
    }

    private fun withdraw(isSelf: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentState = state.value) {
                is SelfTransactionState.Content -> {
                    val data = setupWithdrawData(currentState, isSelf)
                    if (data.amount > currentState.defaultAccount.amount) {
                        addEffect(SelfTransactionEffect.ShowError("AMOUNT MUST BE SMALLER"))
                    } else {
                        makeTransactionUseCase(data)
                            .onSuccess {
                                addEffect(SelfTransactionEffect.ShowCreationToast("TRANSACTION MADE"))
                                addEffect(SelfTransactionEffect.CloseSelf)
                            }
                            .onFailure {
                                Log.e(TAG, it.message.toString())
                                addEffect(
                                    SelfTransactionEffect.ShowError("ERROR DURING MAKING TRANSACTION: ${it.message}")
                                )
                            }
                    }
                    updateState {
                        currentState.copy(
                            isTransactionInfoDialogOpen = false
                        )
                    }
                }

                else -> Unit
            }

        }
    }

    private fun openMoneyInfoDialog(
        state: SelfTransactionState.Content,
        finishAction: SelfTransactionAction,
    ) {
        convertCurrencyByCharCodeUseCase(
            currencyRates = state.currencyRates,
            codeFrom = state.chosenAccount.currencyCode,
            codeTo = state.defaultAccount.currencyCode,
            value = state.amount
        )
            .onSuccess {
                updateState {
                    state.copy(
                        amountForTransaction = it,
                        finishAction = finishAction,
                        isTransactionInfoDialogOpen = true,
                    )
                }
            }
            .onFailure {
                Log.e(TAG, it.message.toString())
                addEffect(SelfTransactionEffect.ShowError("Failed to convert money"))
            }

    }

    private fun setupWithdrawData(
        state: SelfTransactionState.Content,
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