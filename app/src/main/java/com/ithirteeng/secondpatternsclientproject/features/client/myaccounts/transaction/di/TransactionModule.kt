package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.di

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.TransactionViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.presentation.model.TransactionState
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.transaction.stub.TransactionStubViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val transactionModule = module {

    viewModelOf(::TransactionViewModel)

    viewModelOf(::TransactionStubViewModel)
}