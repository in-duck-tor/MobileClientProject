package com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.di

import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.presentation.MyLoansMainViewModel
import com.ithirteeng.secondpatternsclientproject.features.client.myloans.main.stub.LoansMainStubViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val myLoansMainModule = module {

    viewModelOf(::LoansMainStubViewModel)

    viewModelOf(::MyLoansMainViewModel)
}