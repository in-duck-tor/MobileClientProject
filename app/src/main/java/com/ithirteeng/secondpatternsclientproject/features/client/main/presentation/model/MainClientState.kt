package com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState
import com.ithirteeng.secondpatternsclientproject.features.client.main.ui.model.MainClientTab

sealed interface MainClientState : BaseState {

    data object Loading : MainClientState

    data class Content(
        val tabs: List<MainClientTab> = listOf(
            MainClientTab.MyAccounts,
            MainClientTab.MyLoans,
            MainClientTab.Settings,
        ),
        val selectedTab: MainClientTab,
    ) : MainClientState
}
