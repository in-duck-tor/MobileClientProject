package com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase.FetchCurrencyRatesUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.model.Token
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveTokenLocallyUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.SaveUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEffect
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEvent
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashState
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    private val saveTokenLocallyUseCase: SaveTokenLocallyUseCase,
    private val fetchCurrencyRatesUseCase: FetchCurrencyRatesUseCase,
    private val fetchApplicationThemeUseCase: FetchApplicationThemeUseCase,
    private val saveUserLoginUseCase: SaveUserLoginUseCase,
) : BaseViewModel<SplashState, SplashEvent, SplashEffect>() {

    override fun initState(): SplashState = SplashState.Init

    override fun processEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Init -> handleInit()
        }
    }

    private fun handleInit() {
        viewModelScope.launch {
            fetchCurrencyRatesUseCase()
            saveUserLoginUseCase(LOGIN)
            fetchApplicationThemeUseCase(LOGIN)
            saveTokenLocallyUseCase(Token(TOKEN, TOKEN))
            addEffect(SplashEffect.NavigateToMainScreen(getLocalTokenUseCase().accessToken))
        }
    }


    private companion object {

        const val LOGIN = "user_login_test"
        const val TOKEN = "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0.eyJzdWIiOiIxIiwibG9naW4iOiJ0ZXN0X2JhbmtfY2xpZW50IiwiYWNjb3VudF90eXBlIjoiY2xpZW50IiwiY2xpZW50X2lkIjoiZnJvbnQiLCJuYmYiOjE3MDk5ODMwNjQsImV4cCI6MTcxNzc1OTA2NCwiaWF0IjoxNzA5OTgzMDY0LCJpc3MiOiJpbi1kdWNrLXRvciIsImF1ZCI6ImluLWR1Y2stdG9yIn0."
    }
}