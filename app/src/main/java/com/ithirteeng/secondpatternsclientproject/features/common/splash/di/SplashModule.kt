package com.ithirteeng.secondpatternsclientproject.features.common.splash.di

import com.ithirteeng.secondpatternsclientproject.features.common.splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val splashModule = module {

    viewModelOf(::SplashViewModel)
}
