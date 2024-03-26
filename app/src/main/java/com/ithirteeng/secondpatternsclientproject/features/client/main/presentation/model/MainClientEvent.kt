package com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent
import com.ithirteeng.secondpatternsclientproject.features.client.main.ui.model.MainClientTab

sealed interface MainClientEvent : BaseEvent {

    data object Init : MainClientEvent

    data object DataLoaded : MainClientEvent

    sealed interface Ui : MainClientEvent, BaseEvent.Ui {

        data class TabClick(val tab: MainClientTab) : Ui
    }
}
