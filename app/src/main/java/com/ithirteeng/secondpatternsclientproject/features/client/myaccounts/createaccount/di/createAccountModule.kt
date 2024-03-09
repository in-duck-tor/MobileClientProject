package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.di

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.createaccount.presentation.CreateAccountViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val createAccountModule = module {

    viewModelOf(::CreateAccountViewModel)
}