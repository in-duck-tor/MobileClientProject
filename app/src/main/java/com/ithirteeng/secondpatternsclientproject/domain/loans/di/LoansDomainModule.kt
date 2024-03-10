package com.ithirteeng.secondpatternsclientproject.domain.loans.di

import com.ithirteeng.secondpatternsclientproject.data.loans.datasource.LoansStubDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.domain.loans.datasource.LoansStubDatasource
import org.koin.dsl.module

val loansDomainModule = module {

    single<LoansStubDatasource> {
        LoansStubDatasourceImpl(loansDao = get())
    }
}