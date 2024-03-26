package com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEvent

sealed interface SplashEvent : BaseEvent {

    data object Init : SplashEvent
}
