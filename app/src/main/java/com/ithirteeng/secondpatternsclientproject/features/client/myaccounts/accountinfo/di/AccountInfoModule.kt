package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.di

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.presentation.AccountInfoViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.accountinfo.stub.AccountInfoStubViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val accountInfoModule = module {

    viewModelOf(::AccountInfoViewModel)

    viewModelOf(::AccountInfoStubViewModel)
}