package com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource

import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.Currency

interface ExchangeLocalDatasource {

    suspend fun insertRates(list: List<Currency>)

    suspend fun getRates(): List<Currency>
}