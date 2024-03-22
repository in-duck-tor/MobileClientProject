package com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource.ExchangeRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.toCurrency

class GetCurrencyRatesUseCase(
    private val remoteDatasource: ExchangeRemoteDatasource
) {

    suspend operator fun invoke() = provideResult {
        val currencyRate = remoteDatasource.getCurrencyRates()
        currencyRate.valuteStrings.map {
            it.toCurrency()
        }
    }
}