package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.stub

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.AccountsFilter
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AccountsMainStubViewModel(
    getUserLoginUseCase: GetUserLoginUseCase,
    private val observeAccountsUseCase: ObserveAccountsUseCase,
) : BaseViewModel<MyAccountsMainState, MyAccountsMainEvent, MyAccountsMainEffect>() {

    override fun initState(): MyAccountsMainState = MyAccountsMainState.Loading

    private lateinit var accounts: List<Account>

    private val login = getUserLoginUseCase()

    override fun processEvent(event: MyAccountsMainEvent) {
        when (event) {
            is MyAccountsMainEvent.Init -> handleInit()
            is MyAccountsMainEvent.DataLoaded -> handleDataLoaded(event)
            is MyAccountsMainEvent.Ui.AccountClick -> handleAccountClick(event)
            is MyAccountsMainEvent.Ui.CreateAccountButtonClick -> handleCreateAccountButtonClick()
            is MyAccountsMainEvent.Ui.AccountsFilterChange -> handleAccountFilterChange(event)
            else -> {}
        }
    }

    private fun handleInit() {
        viewModelScope.launch(Dispatchers.IO) {
            observeAccounts()
        }
    }

    private fun handleDataLoaded(event: MyAccountsMainEvent.DataLoaded) {
        when (val currentState = state.value) {
            is MyAccountsMainState.Loading -> updateState {
                MyAccountsMainState.Content(
                    accounts = event.accounts.sortedBy { it.number },
                    clientId = event.clientId
                )
            }

            is MyAccountsMainState.Content -> updateState {
                currentState.copy(accounts = event.accounts.sortedBy { it.number })
            }
        }
    }

    private suspend fun observeAccounts() {

        observeAccountsUseCase.invoke(login)
            .onSuccess { flow ->
                flow.collectLatest { accounts ->
                    if (accounts.isNotEmpty()) {
                        processEvent(
                            MyAccountsMainEvent.DataLoaded(
                                clientId = login,
                                accounts = (state.value as? MyAccountsMainState.Content)?.filterState?.let { filter ->
                                    accounts
                                        .filter { it.state == filter }
                                        .sortedBy { it.number }
                                } ?: accounts.sortedBy { it.number }
                            )
                        )
                        this.accounts = accounts
                    }
                }

            }
            .onFailure {
                Log.e(TAG, it.message.toString())
                addEffect(
                    MyAccountsMainEffect.ShowError(
                        R.string.fetching_error,
                        it.message.toString()
                    )
                )
            }
    }

    private fun handleCreateAccountButtonClick() {
        when (val currentState = state.value) {
            is MyAccountsMainState.Content -> addEffect(
                MyAccountsMainEffect.NavigateToCreateAccountScreen(currentState.clientId)
            )

            else -> Unit
        }
    }

    private fun handleAccountClick(event: MyAccountsMainEvent.Ui.AccountClick) {
        when (val currentState = state.value) {
            is MyAccountsMainState.Content -> addEffect(
                MyAccountsMainEffect.NavigateToAccountInfoScreen(
                    currentState.clientId,
                    event.accountId
                )
            )

            else -> Unit
        }
    }

    private fun handleAccountFilterChange(event: MyAccountsMainEvent.Ui.AccountsFilterChange) {
        val accountState = when (event.filter) {
            AccountsFilter.ALL -> null
            AccountsFilter.ACTIVE -> AccountState.active
            AccountsFilter.CLOSED -> AccountState.closed
            AccountsFilter.FROZEN -> AccountState.frozen
        }
        when (state.value) {
            is MyAccountsMainState.Content -> {
                updateState {
                    (state.value as MyAccountsMainState.Content).copy(
                        filter = event.filter,
                        filterState = accountState,
                        accounts = accountState?.let {
                            accounts
                                .filter { it.state == accountState }
                                .sortedBy { it.number }
                        } ?: accounts.sortedBy { it.number }
                    )
                }
            }

            else -> Unit
        }
    }

    private companion object {

        const val TAG = "STUB - MyAccountsMainViewModel"
    }
}
