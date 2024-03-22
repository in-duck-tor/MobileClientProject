package com.ithirteeng.secondpatternsclientproject.data.exchange.datasource

import com.ithirteeng.secondpatternsclientproject.data.exchange.entity.toDomain
import com.ithirteeng.secondpatternsclientproject.data.exchange.entity.toEntity
import com.ithirteeng.secondpatternsclientproject.data.exchange.storage.CurrencyDao
import com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource.ExchangeLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.exchange.model.Currency

class ExchangeLocalDatasourceImpl(
    private val dao: CurrencyDao,
) : ExchangeLocalDatasource {

    override suspend fun insertRates(list: List<Currency>) {
        dao.insertRates(list.map { it.toEntity() })
    }

    override suspend fun getRates(): List<Currency> {
        return dao.getCurrencyRates().map { it.toDomain() }
    }
}