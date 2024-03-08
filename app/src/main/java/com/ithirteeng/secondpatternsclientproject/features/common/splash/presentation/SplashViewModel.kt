package com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEffect
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEvent
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel<SplashState, SplashEvent, SplashEffect>() {

    override fun initState(): SplashState = SplashState.Init

    override fun processEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Init -> handleInit()
        }
    }

    private fun handleInit() {
        viewModelScope.launch {
            delay(1000)
            addEffect(SplashEffect.OnAuthorized)
        }
    }
}