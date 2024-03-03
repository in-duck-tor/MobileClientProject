package com.ithirteeng.secondpatternsclientproject.features.client.main.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientEffect
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientEvent
import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model.MainClientState
import com.ithirteeng.secondpatternsclientproject.features.client.main.ui.model.MainClientTab
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            is MainClientState.Loading -> viewModelScope.launch {
                delay(1000)
                processEvent(MainClientEvent.DataLoaded)
            }
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
        when (state.value) {
            is MainClientState.Content -> updateState {
                (this as MainClientState.Content).copy(selectedTab = tab)
            }
            else -> Unit
        }
    }
}
