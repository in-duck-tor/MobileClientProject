package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.di

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.MyAccountsMainViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.stub.AccountsMainStubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val myAccountsMainModule = module {

    viewModel {
        MyAccountsMainViewModel(
            observeAccountsUseCase = get(),
            getLocalTokenUseCase = get(),
            fetchAccountsUseCase = get(),
        )
    }

    viewModelOf(::AccountsMainStubViewModel)
}
