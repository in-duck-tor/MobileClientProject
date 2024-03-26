package com.ithirteeng.secondpatternsclientproject.data.exchange.datasource

import com.ithirteeng.secondpatternsclientproject.data.exchange.api.ExchangeNetworkService
import com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource.ExchangeRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.CurrencyRate

class ExchangeRemoteDatasourceImpl(
    private val networkService: ExchangeNetworkService,
) : ExchangeRemoteDatasource {

    override suspend fun getCurrencyRates(): CurrencyRate {
        return networkService.getDailyRates()
    }
}