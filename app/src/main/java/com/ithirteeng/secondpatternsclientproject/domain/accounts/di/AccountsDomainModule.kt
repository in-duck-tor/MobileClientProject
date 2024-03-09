package com.ithirteeng.secondpatternsclientproject.domain.accounts.di

import com.ithirteeng.secondpatternsclientproject.data.accounts.repository.AccountsLocalDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.accounts.repository.AccountsRemoteDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.CreateAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.FetchAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.FetchTransactionsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.ObserveTransactionsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val accountsDomainModule = module {
    single<AccountsRemoteDatasource> {
        AccountsRemoteDatasourceImpl(service = get())
    }

    single<AccountsLocalDatasource> {
        AccountsLocalDatasourceImpl(
            accountsDao = get(),
            transactionsDao = get()
        )
    }

    factoryOf(::FetchAccountsUseCase)
    factoryOf(::ObserveAccountsUseCase)
    factoryOf(::ObserveTransactionsUseCase)
    factoryOf(::FetchTransactionsUseCase)
    factoryOf(::CreateAccountUseCase)
}