package com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveTokenLocallyUseCase
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEffect
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEvent
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    private val saveTokenLocallyUseCase: SaveTokenLocallyUseCase,
) : BaseViewModel<SplashState, SplashEvent, SplashEffect>() {

    override fun initState(): SplashState = SplashState.Init

    override fun processEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Init -> handleInit()
        }
    }

    private fun handleInit() {
        viewModelScope.launch {
            delay(500)
            saveTokenLocallyUseCase("1")
            addEffect(SplashEffect.OnAuthorized(getLocalTokenUseCase()))
        }
    }
}