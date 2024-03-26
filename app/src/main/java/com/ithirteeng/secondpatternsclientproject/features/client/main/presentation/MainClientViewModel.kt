package com.ithirteeng.secondpatternsclientproject.features.client.main.presentation

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientEffect
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientEvent
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientState
import com.ithirteeng.secondpatternsclientproject.features.client.main.ui.model.MainClientTab
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.navigation.MyAccountsDestination
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.navigation.MyLoansDestination
import com.ithirteeng.secondpatternsclientproject.features.common.settings.navigation.SettingsDestination

class MainClientViewModel : BaseViewModel<MainClientState, MainClientEvent, MainClientEffect>() {

    override fun initState(): MainClientState = MainClientState.Loading

    override fun processEvent(event: MainClientEvent) {
        when (event) {
            is MainClientEvent.Init -> handleInit()
            is MainClientEvent.DataLoaded -> handleDataLoaded()
            is MainClientEvent.Ui.TabClick -> handleTabClick(event.tab)
        }
    }

    private fun handleInit() {
        when (state.value) {
            is MainClientState.Loading -> processEvent(MainClientEvent.DataLoaded)
            else -> Unit
        }
    }

    private fun handleDataLoaded() {
        when (state.value) {
            is MainClientState.Loading -> updateState {
                MainClientState.Content(selectedTab = MainClientTab.MyAccounts)
            }

            else -> Unit
        }
    }

    private fun handleTabClick(tab: MainClientTab) {
        when (val currentState = state.value) {
            is MainClientState.Content -> updateState {
                currentState.copy(selectedTab = tab)
            }

            else -> Unit
        }
        when (tab) {
            is MainClientTab.MyAccounts -> {
                addEffect(MainClientEffect.NavigateToClientScreen(MyAccountsDestination.route))
            }

            is MainClientTab.MyLoans -> {
                addEffect(MainClientEffect.NavigateToClientScreen(MyLoansDestination.route))
            }

            is MainClientTab.Settings -> {
                addEffect(MainClientEffect.NavigateToClientScreen(SettingsDestination.route))
            }
        }
    }
}
