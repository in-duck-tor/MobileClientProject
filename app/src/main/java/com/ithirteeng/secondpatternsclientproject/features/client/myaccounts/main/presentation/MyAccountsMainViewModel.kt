package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ithirteeng.secondpatternsclientproject.R
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.data.notifications.model.AppRegistrationModel
import com.ithirteeng.secondpatternsclientproject.data.notifications.service.NotificationService
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.Account
import com.ithirteeng.secondpatternsclientproject.domain.accounts.model.account.AccountState
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.FetchAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.MakeAccountHiddenUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.MakeAccountVisibleUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.AccountsFilter
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEffect
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainEvent
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.model.MyAccountsMainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyAccountsMainViewModel(
    getUserLoginUseCase: GetUserLoginUseCase,
    private val fetchAccountsUseCase: FetchAccountsUseCase,
    private val observeAccountsUseCase: ObserveAccountsUseCase,
    private val makeAccountHiddenUseCase: MakeAccountHiddenUseCase,
    private val makeAccountVisibleUseCase: MakeAccountVisibleUseCase,
    private val notificationService: NotificationService,
) : BaseViewModel<MyAccountsMainState, MyAccountsMainEvent, MyAccountsMainEffect>() {

    override fun initState(): MyAccountsMainState = MyAccountsMainState.Loading

    private lateinit var accounts: List<Account>

    private val login = getUserLoginUseCase()

    override fun processEvent(event: MyAccountsMainEvent) {
        when (event) {
            is MyAccountsMainEvent.Init -> {
                handleInit()
            }

            is MyAccountsMainEvent.DataLoaded -> handleDataLoaded(event)
            is MyAccountsMainEvent.Ui.AccountClick -> handleAccountClick(event)
            is MyAccountsMainEvent.Ui.CreateAccountButtonClick -> handleCreateAccountButtonClick()
            is MyAccountsMainEvent.Ui.AccountsFilterChange -> handleAccountFilterChange(event)
            is MyAccountsMainEvent.Ui.HiddenAccountVisibilityChange -> handleHiddenAccountsVisibilityChange(
                event
            )

            is MyAccountsMainEvent.Ui.ChangeAccountVisibility -> handleAccountVisibilityChange(event)
        }
    }

    private fun handleInit() {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                launch(Dispatchers.IO) {
                    notificationService.registerAppForNotifications(
                        AppRegistrationModel(
                            registrationToken = token,
                            applicationId = "inductorbank"
                        )
                    )
                    Log.d("Bullshit", "token: $token sent")
                }
            })
            fetchAccountsUseCase(login)
                .onFailure {
                    Log.e(TAG, it.message.toString())
                    addEffect(
                        MyAccountsMainEffect.ShowError(
                            R.string.fetching_error,
                            it.message.toString()
                        )
                    )
                }
            observeAccounts()
        }
    }

    private fun handleDataLoaded(event: MyAccountsMainEvent.DataLoaded) {
        when (val currentState = state.value) {
            is MyAccountsMainState.Loading -> updateState {
                MyAccountsMainState.Content(accounts = event.accounts, clientId = event.clientId)
            }

            is MyAccountsMainState.Content -> updateState {
                currentState.copy(accounts = event.accounts)
            }
        }
    }

    private fun handleAccountVisibilityChange(event: MyAccountsMainEvent.Ui.ChangeAccountVisibility) {
        viewModelScope.launch(Dispatchers.IO) {
            if (event.account.isHidden) {
                makeAccountVisibleUseCase(login, event.account.copy(isHidden = false))
            } else {
                makeAccountHiddenUseCase(login, event.account.copy(isHidden = true))
            }
        }
    }

    private fun handleHiddenAccountsVisibilityChange(
        event: MyAccountsMainEvent.Ui.HiddenAccountVisibilityChange,
    ) {
        when (val currentState = state.value) {
            is MyAccountsMainState.Content -> updateState {
                val newState = currentState.copy(
                    showHidden = event.isVisible,
                )
                newState.copy(
                    accounts = getSortedAccounts(newState)
                )
            }

            is MyAccountsMainState.Loading -> Unit
        }
    }

    private suspend fun observeAccounts() {
        observeAccountsUseCase.invoke(login)
            .onSuccess { flow ->
                flow.collectLatest { accounts ->
                    this.accounts = accounts
                    if (accounts.isNotEmpty()) {
                        processEvent(
                            MyAccountsMainEvent.DataLoaded(
                                clientId = login,
                                accounts = when (val currentState = state.value) {
                                    is MyAccountsMainState.Content -> {
                                        getSortedAccounts(currentState)
                                    }

                                    is MyAccountsMainState.Loading -> accounts.filter { !it.isHidden }
                                }
                            )
                        )
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

    private fun getSortedAccounts(currentState: MyAccountsMainState.Content): List<Account> {
        return if (currentState.showHidden) {
            if (currentState.filterState == null) {
                accounts
            } else {
                accounts.filter { it.state == currentState.filterState }
            }
        } else {
            if (currentState.filterState == null) {
                accounts.filter { !it.isHidden }
            } else {
                accounts.filter { it.state == currentState.filterState && !it.isHidden }
            }
        }.sortedBy { it.number }
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
        when (val currentState = state.value) {
            is MyAccountsMainState.Content -> {
                updateState {
                    val newState = currentState.copy(
                        filter = event.filter,
                        filterState = accountState,
                    )
                    newState.copy(
                        accounts = getSortedAccounts(newState)
                    )
                }
            }

            else -> Unit
        }
    }

    private companion object {

        const val TAG = "MyAccountsMainViewModel"
    }
}
