package com.ithirteeng.secondpatternsclientproject.domain.theme.di

import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.FetchApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.ObserveApplicationThemeUseCase
import com.ithirteeng.secondpatternsclientproject.domain.theme.usecase.SetApplicationThemeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val themeDomainModule = module {

    factoryOf(::ObserveApplicationThemeUseCase)
    factoryOf(::SetApplicationThemeUseCase)
    factoryOf(::FetchApplicationThemeUseCase)
}