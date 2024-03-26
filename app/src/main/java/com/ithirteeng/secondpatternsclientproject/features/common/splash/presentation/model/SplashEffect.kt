package com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface SplashEffect : BaseEffect {

    data class NavigateToMainScreen(val token: String) : SplashEffect

    data object NavigateToAuthorizationScreen : SplashEffect
}
