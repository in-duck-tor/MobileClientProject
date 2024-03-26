package com.ithirteeng.secondpatternsclientproject.data.loans.di

import com.ithirteeng.secondpatternsclientproject.common.network.model.ConnectionType
import com.ithirteeng.secondpatternsclientproject.common.network.createRetrofitService
import com.ithirteeng.secondpatternsclientproject.common.storage.BankDatabase
import com.ithirteeng.secondpatternsclientproject.data.loans.api.LoansNetworkService
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val loansDataModule = module {

    singleOf(BankDatabase::loanDao)

    single {
        createRetrofitService<LoansNetworkService>(
            retrofit = get(named(ConnectionType.UNAUTHORIZED))
        )
    }
}