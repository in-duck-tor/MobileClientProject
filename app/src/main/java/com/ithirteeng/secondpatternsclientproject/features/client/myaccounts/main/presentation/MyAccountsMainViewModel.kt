package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.data.accounts.AccountsStubs
import com.ithirteeng.secondpatternsclientproject.data.accounts.service.AccountsNetworkService
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.AccountInfo
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.AccountsFilter
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyAccountsMainViewModel(
    private val service: AccountsNetworkService,
) : BaseViewModel<MyAccountsMainState, MyAccountsMainEvent, MyAccountsMainEffect>() {

    override fun initState(): MyAccountsMainState = MyAccountsMainState.Loading

    private lateinit var accountsList: List<AccountInfo>

    override fun processEvent(event: MyAccountsMainEvent) {
        when (event) {
            is MyAccountsMainEvent.Init -> handleInit(event)
            is MyAccountsMainEvent.DataLoaded -> handleDataLoaded(event)
            is MyAccountsMainEvent.Ui.AccountClick -> handleAccountClick(event)
            is MyAccountsMainEvent.Ui.CreateAccountButtonClick -> handleCreateAccountButtonClick()
            is MyAccountsMainEvent.Ui.AccountsFilterChange -> handleAccountFilterChange(event)
        }
    }

    private fun handleInit(event: MyAccountsMainEvent.Init) {
        viewModelScope.launch {
            delay(1500)
            accountsList = AccountsStubs.createAccountsList()
            processEvent(
                MyAccountsMainEvent.DataLoaded(
                    event.clientId,
                    AccountsStubs.createAccountsList()
                )
            )
        }
    }

    private fun handleDataLoaded(event: MyAccountsMainEvent.DataLoaded) {
        when (val currentState = state.value) {
            is MyAccountsMainState.Loading -> updateState {
                MyAccountsMainState.Content(accounts = event.data, clientId = event.clientId)
            }

            is MyAccountsMainState.Content -> updateState {
                currentState.copy(accounts = event.data)
            }
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
        val isActive = when (event.filter) {
            AccountsFilter.ALL -> null
            AccountsFilter.ACTIVE -> true
            AccountsFilter.INACTIVE -> false
        }
        when (val currentState = state.value) {
            is MyAccountsMainState.Content -> {
                updateState {
                    currentState.copy(
                        filter = event.filter,
                        accounts = isActive?.let { accountsList.filter { it.isActive == isActive } }
                            ?: accountsList

                    )
                }
            }

            else -> Unit
        }
    }
}
