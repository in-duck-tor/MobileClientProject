package com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseState

sealed interface SplashState: BaseState {

    data object Init : SplashState
}
