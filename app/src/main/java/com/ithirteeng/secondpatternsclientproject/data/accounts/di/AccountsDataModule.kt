package com.ithirteeng.secondpatternsclientproject.data.accounts.di

import com.ithirteeng.secondpatternsclientproject.common.network.createRetrofitService
import com.ithirteeng.secondpatternsclientproject.common.network.model.ConnectionType
import com.ithirteeng.secondpatternsclientproject.common.storage.BankDatabase
import com.ithirteeng.secondpatternsclientproject.data.accounts.api.AccountsNetworkService
import com.ithirteeng.secondpatternsclientproject.data.accounts.api.AccountsV2NetworkService
import com.ithirteeng.secondpatternsclientproject.data.accounts.datasource.SocketsDatasource
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val accountsDataModule = module {

    single {
        createRetrofitService<AccountsNetworkService>(
            retrofit = get(named(ConnectionType.AUTHORIZED.name))
        )
    }

    single {
        createRetrofitService<AccountsV2NetworkService>(
            retrofit = get(named(ConnectionType.AUTHORIZED_V2.name))
        )
    }

    singleOf(BankDatabase::accountsDao)
    singleOf(BankDatabase::transactionsDao)
}