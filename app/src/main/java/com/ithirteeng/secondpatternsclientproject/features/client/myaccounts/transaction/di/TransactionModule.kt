package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.di

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.global.presentation.GlobalTransactionViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.presentation.SelfTransactionViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.self.stub.TransactionStubViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val transactionModule = module {

    viewModelOf(::SelfTransactionViewModel)
    viewModelOf(::GlobalTransactionViewModel)
    viewModelOf(::TransactionStubViewModel)
}