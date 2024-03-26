package com.ithirteeng.secondpatternsclientproject.domain.exchange.usecase

import com.ithirteeng.secondpatternsclientproject.common.domain.provideResult
import com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource.ExchangeLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource.ExchangeRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.toCurrency

class FetchCurrencyRatesUseCase(
    private val remoteDatasource: ExchangeRemoteDatasource,
    private val localDatasource: ExchangeLocalDatasource,
) {

    suspend operator fun invoke() = provideResult {
        val remoteRates = remoteDatasource.getCurrencyRates().valuteStrings.map { it.toCurrency() }
        localDatasource.insertRates(remoteRates)
    }
}
