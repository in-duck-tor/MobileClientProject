package com.ithirteeng.secondpatternsclientproject.features.client.main.di

import com.ithirteeng.secondpatternsclientproject.features.client.main.presentation.MainClientViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainClientModule = module {

    viewModel {
        MainClientViewModel()
    }
}
