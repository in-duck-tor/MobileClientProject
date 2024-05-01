package com.ithirteeng.secondpatternsclientproject.domain.accounts.di

import com.ithirteeng.secondpatternsclientproject.data.accounts.datasource.AccountsLocalDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.accounts.datasource.AccountsRemoteDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.accounts.datasource.AccountsStubDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsRemoteDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.datasource.AccountsStubDatasource
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.GetBanksInfoUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.GetCurrencyCodesUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ChangeAccountStateUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.CreateAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.CreateAccountV2UseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.FetchAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.GetAccountUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.MakeAccountHiddenUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.MakeAccountVisibleUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.account.ObserveAccountsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub.CreateAccountStubUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub.MakeTransactionBetweenAccountsStubUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub.ReplenishAccountStubUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.stub.WithdrawFromAccountStubUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.FetchTransactionsUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.MakeTransactionUseCase
import com.ithirteeng.secondpatternsclientproject.domain.accounts.usecase.transaction.ObserveTransactionsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val accountsDomainModule = module {
    single<AccountsRemoteDatasource> {
        AccountsRemoteDatasourceImpl(
            service = get(),
            firebaseDatabase = get(),
            serviceV2 = get()
        )
    }

    single<AccountsLocalDatasource> {
        AccountsLocalDatasourceImpl(
            accountsDao = get(),
            transactionsDao = get()
        )
    }

    single<AccountsStubDatasource> {
        AccountsStubDatasourceImpl(
            accountsDao = get(),
            transactionsDao = get()
        )
    }

    factoryOf(::FetchAccountsUseCase)
    factoryOf(::ObserveAccountsUseCase)
    factoryOf(::CreateAccountUseCase)
    factoryOf(::CreateAccountV2UseCase)
    factoryOf(::GetAccountUseCase)
    factoryOf(::ChangeAccountStateUseCase)

    factoryOf(::FetchTransactionsUseCase)
    factoryOf(::ObserveTransactionsUseCase)
    factoryOf(::MakeTransactionUseCase)

    factoryOf(::CreateAccountStubUseCase)
    factoryOf(::MakeTransactionBetweenAccountsStubUseCase)
    factoryOf(::ReplenishAccountStubUseCase)
    factoryOf(::WithdrawFromAccountStubUseCase)

    factoryOf(::GetCurrencyCodesUseCase)

    factoryOf(::MakeAccountVisibleUseCase)
    factoryOf(::MakeAccountHiddenUseCase)

    factoryOf(::GetBanksInfoUseCase)
}
