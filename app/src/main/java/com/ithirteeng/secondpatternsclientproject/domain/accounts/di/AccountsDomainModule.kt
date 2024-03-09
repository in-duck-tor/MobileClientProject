package com.ithirteeng.secondpatternsclientproject.domain.accounts.di

import com.ithirteeng.secondpatternsclientproject.data.accounts.repository.AccountsLocalDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.accounts.repository.AccountsRemoteDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.repository.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ChangeAccountStateUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.CreateAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.FetchAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.FetchTransactionsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.GetAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.MakeTransactionUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.ObserveTransactionsUseCase
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
    factoryOf(::CreateAccountUseCase)
    factoryOf(::GetAccountUseCase)
    factoryOf(::ChangeAccountStateUseCase)

    factoryOf(::FetchTransactionsUseCase)
    factoryOf(::ObserveTransactionsUseCase)
    factoryOf(::MakeTransactionUseCase)
}
