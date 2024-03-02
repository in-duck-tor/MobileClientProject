package com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.model

import com.ithirteeng.secondpatternsclientproject.common.architecture.BaseEffect

sealed interface SplashEffect : BaseEffect {

    data object OnAuthorized : SplashEffect

    data object OnUnauthorized : SplashEffect
}
