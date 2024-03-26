package com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource.ExchangeLocalDatasource

class GetCurrencyRatesUseCase(
    private val localDatasource: ExchangeLocalDatasource,
) {

    suspend operator fun invoke() = provideResult {
        localDatasource.getRates()
    }
}