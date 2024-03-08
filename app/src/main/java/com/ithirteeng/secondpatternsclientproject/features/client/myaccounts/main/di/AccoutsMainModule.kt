package com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.di

import com.ithirteeng.secondpatternsclientproject.common.network.ConnectionType
import com.ithirteeng.secondpatternsclientproject.common.network.createRetrofitService
import com.ithirteeng.secondpatternsclientproject.data.accounts.service.AccountsNetworkService
import com.ithirteeng.secondpatternsclientproject.features.client.myaccounts.main.presentation.MyAccountsMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val myAccountsMainModule = module {

    single { createRetrofitService<AccountsNetworkService>(retrofit = get(named(ConnectionType.UNAUTHORIZED.name))) }

    viewModel {
        MyAccountsMainViewModel(
            service = get()
        )
    }
}
