package com.ithirteeng.secondpatternsclientproject.data.loans.di

import com.ithirteeng.secondpatternsclientproject.common.network.ConnectionType
import com.ithirteeng.secondpatternsclientproject.common.network.createRetrofitService
import com.ithirteeng.secondpatternsclientproject.data.loans.api.LoansNetworkService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val loansDataModule = module {

    single {
        createRetrofitService<LoansNetworkService>(
            retrofit = get(named(ConnectionType.UNAUTHORIZED))
        )
    }
}