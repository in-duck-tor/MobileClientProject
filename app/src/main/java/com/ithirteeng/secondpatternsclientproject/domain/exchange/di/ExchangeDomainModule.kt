package com.ithirteeng.secondpatternsclientproject.domain.exchange.di

import com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase.ConvertCurrencyByCharCode
import com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase.FetchCurrencyRatesUseCase
import com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase.GetCurrencyRatesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val exchangeDomainModule = module {

    factoryOf(::GetCurrencyRatesUseCase)
    factoryOf(::ConvertCurrencyByCharCode)
    factoryOf(::FetchCurrencyRatesUseCase)
}