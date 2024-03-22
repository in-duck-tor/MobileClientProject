package com.ithirteeng.secondpatternsclientproject.data.exchange.di

import com.ithirteeng.secondpatternsclientproject.common.network.createRetrofitService
import com.ithirteeng.secondpatternsclientproject.common.network.model.ConnectionType
import com.ithirteeng.secondpatternsclientproject.data.exchange.api.ExchangeNetworkService
import com.ithirteeng.secondpatternsclientproject.data.exchange.datasource.ExchangeRemoteDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.domain.exchange.datasource.ExchangeRemoteDatasource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val exchangeDataModule = module {

    single {
        createRetrofitService<ExchangeNetworkService>(get(named(ConnectionType.UNAUTHORIZED_XML.name)))
    }

    single<ExchangeRemoteDatasource> { ExchangeRemoteDatasourceImpl(get()) }
}