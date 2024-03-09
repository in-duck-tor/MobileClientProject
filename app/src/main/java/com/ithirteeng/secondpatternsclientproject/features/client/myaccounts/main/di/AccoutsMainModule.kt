package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.di

import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.MyAccountsMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myAccountsMainModule = module {

    viewModel {
        MyAccountsMainViewModel(
            repository = get()
        )
    }
}
