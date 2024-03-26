package com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.di

import com.ithirteeng.secondpatternsclientproject.features.client.myloans.createloan.presentation.CreateLoanViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val createLoanModule = module {

    viewModelOf(::CreateLoanViewModel)
}