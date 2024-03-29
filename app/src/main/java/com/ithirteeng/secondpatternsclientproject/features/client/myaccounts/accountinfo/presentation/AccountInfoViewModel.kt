package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ChangeAccountStateUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.GetAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.FetchTransactionsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.ObserveTransactionsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model.AccountAction
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model.AccountInfoEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model.AccountInfoEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.model.AccountInfoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountInfoViewModel(
    getUserLoginUseCase: GetUserLoginUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val fetchTransactionsUseCase: FetchTransactionsUseCase,
    private val changeAccountStateUseCase: ChangeAccountStateUseCase,
) : BaseViewModel<AccountInfoState, AccountInfoEvent, AccountInfoEffect>() {

    override fun initState(): AccountInfoState = AccountInfoState.Loading

    private val token = getUserLoginUseCase()

    @SuppressLint("NewApi")
    override fun processEvent(event: AccountInfoEvent) {
        when (event) {
            is AccountInfoEvent.Init -> handleInit(event)
            is AccountInfoEvent.DataLoaded -> handleDataLoaded(event)
            is AccountInfoEvent.Ui.MakeTransactionButtonClick -> handleMakeTransactionButtonClick()
            is AccountInfoEvent.Ui.ChangeAccountState -> handleChangeAccountStateButtonClick(
                action = event.action
            )
        }
    }

    private fun handleInit(event: AccountInfoEvent.Init) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchTransactionsUseCase(event.accountNumber)
                .onSuccess {
                    Log.d("TESTS", "SHIT")
                }
                .onFailure {
                    Log.e(TAG, it.message.toString())
                    addEffect(AccountInfoEffect.ShowError(R.string.fetching_error))
                }
            getAccountUseCase(event.accountNumber)
                .onSuccess { account ->
                    observeTransactions(account = account)
                }
                .onFailure {
                    Log.e(TAG, it.message.toString())
                    addEffect(AccountInfoEffect.ShowError(R.string.fetching_error))
                }
        }
    }

    private suspend fun observeTransactions(account: Account) {
        observeTransactionsUseCase(accountNumber = account.number)
            .onSuccess { transactionsFlow ->
                transactionsFlow.collect { transactions ->
                    processEvent(
                        AccountInfoEvent.DataLoaded(
                            account = account,
                            transactions = transactions,
                        )
                    )
                }
            }
    }

    private fun handleDataLoaded(event: AccountInfoEvent.DataLoaded) {
        when (val currentState = state.value) {
            is AccountInfoState.Content -> updateState {
                currentState.copy(
                    account = event.account,
                    transactions = event.transactions,
                    actions = getCorrectActions(event.account)
                )
            }

            is AccountInfoState.Loading -> updateState {
                AccountInfoState.Content(
                    account = event.account,
                    transactions = event.transactions,
                    actions = getCorrectActions(event.account)
                )
            }
        }
    }

    private fun getCorrectActions(account: Account): List<AccountAction> {
        val actions = mutableListOf(
            AccountAction.FREEZE,
            AccountAction.UNFREEZE,
            AccountAction.CLOSE
        )
        when (account.state) {
            AccountState.frozen -> actions.remove(AccountAction.FREEZE)
            AccountState.closed -> actions.remove(AccountAction.CLOSE)
            AccountState.active -> actions.remove(AccountAction.UNFREEZE)
        }
        return actions.toList()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun handleChangeAccountStateButtonClick(
        action: AccountAction,
    ) {
        val accountState = when (action) {
            AccountAction.CLOSE -> AccountState.closed
            AccountAction.FREEZE -> AccountState.frozen
            AccountAction.UNFREEZE -> AccountState.active
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentState = state.value) {
                is AccountInfoState.Content -> {
                    changeAccountStateUseCase(
                        action = action,
                        account = currentState.account.copy(
                            state = accountState
                        ),
                        token = token
                    )
                        .onSuccess {
                            updateState {
                                currentState.copy(
                                    account = currentState.account.copy(
                                        state = accountState
                                    ),
                                    actions = getCorrectActions(
                                        currentState.account.copy(
                                            state = accountState
                                        )
                                    )
                                )
                            }
                        }
                        .onFailure {
                            Log.e(TAG, it.message.toString())
                            addEffect(
                                AccountInfoEffect.ShowError(
                                    R.string.error_with_account_data_uploading,
                                    it.message.toString()
                                )
                            )
                        }
                }

                else -> Unit
            }
        }
    }

    private fun handleMakeTransactionButtonClick() {
        when (val currentState = state.value) {
            is AccountInfoState.Content -> addEffect(
                AccountInfoEffect.NavigateToTransactionScreen(currentState.account.number)
            )

            else -> Unit
        }
    }

    private companion object {

        const val TAG = "AccountInfoViewModel"
    }

}
