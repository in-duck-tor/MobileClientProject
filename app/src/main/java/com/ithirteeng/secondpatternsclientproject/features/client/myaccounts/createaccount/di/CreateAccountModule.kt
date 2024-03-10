package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.di

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.CreateAccountViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.stub.CreateAccountStubViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val createAccountModule = module {

    viewModelOf(::CreateAccountViewModel)

    viewModelOf(::CreateAccountStubViewModel)
}