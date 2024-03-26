package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.di

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.MyAccountsMainViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.stub.AccountsMainStubViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val myAccountsMainModule = module {

    viewModelOf(::MyAccountsMainViewModel)
    viewModelOf(::AccountsMainStubViewModel)
}
