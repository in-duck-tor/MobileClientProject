package com.ithirteeng.secondpatternsclientproject.data.loans.di

import com.ithirteeng.secondpatternsclientproject.common.network.createRetrofitService
import com.ithirteeng.secondpatternsclientproject.common.network.model.ConnectionType
import com.ithirteeng.secondpatternsclientproject.common.storage.BankDatabase
import com.ithirteeng.secondpatternsclientproject.data.loans.api.LoansNetworkService
import com.ithirteeng.secondpatternsclientproject.data.loans.datasource.LoansRemoteDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.loans.datasource.LoansStubDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansStubDatasource
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val loansDataModule = module {

    single {
        createRetrofitService<LoansNetworkService>(
            retrofit = get(named(ConnectionType.AUTHORIZED.name))
        )
    }

    singleOf(BankDatabase::loanDao)


    singleOf(::LoansRemoteDatasourceImpl) bind LoansRemoteDatasource::class

    singleOf(::LoansStubDatasourceImpl) bind LoansStubDatasource::class
}