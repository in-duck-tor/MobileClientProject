package com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.di

import com.ithirteeng.secondpatternsclientproject.features.client.myloans.loaninfo.presentation.LoanInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loanInfoModule = module {

    viewModelOf(::LoanInfoViewModel)
}