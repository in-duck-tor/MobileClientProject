package com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation

import androidx.lifecycle.viewModelScope
import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseViewModel
import com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase.FetchCurrencyRatesUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.model.Theme
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.SetApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetLocalTokenUseCase
import com.ithirteeng.secondpatternsclientproject.domain.user.usecase.GetUserLoginUseCase
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEffect
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashEvent
import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model.SplashState
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getLocalTokenUseCase: GetLocalTokenUseCase,
    private val getUserLoginUseCase: GetUserLoginUseCase,
    private val fetchCurrencyRatesUseCase: FetchCurrencyRatesUseCase,
    private val fetchApplicationThemeUseCase: FetchApplicationThemeUseCase,
    private val setApplicationThemeUseCase: SetApplicationThemeUseCase,
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
            getLogin()?.let {
                fetchApplicationThemeUseCase(it)
                addEffect(SplashEffect.NavigateToMainScreen(it))
            }
            if (getLogin() == null) {
                setApplicationThemeUseCase(Theme.AUTO, true)
            }
        }
    }

    private fun getLogin(): String? {
        return if (getLocalTokenUseCase() != null) {
            getUserLoginUseCase()
        } else {
            addEffect(SplashEffect.NavigateToAuthorizationScreen)
            null
        }
    }
}