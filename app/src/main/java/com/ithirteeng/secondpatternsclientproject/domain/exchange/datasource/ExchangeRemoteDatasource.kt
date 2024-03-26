package com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource

import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.CurrencyRate

interface ExchangeRemoteDatasource {

    suspend fun getCurrencyRates(): CurrencyRate
}