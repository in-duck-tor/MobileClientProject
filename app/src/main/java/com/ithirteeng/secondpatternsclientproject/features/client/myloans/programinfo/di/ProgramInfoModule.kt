package com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.di

import com.ithirteeng.secondpatternsclientproject.features.client.myloans.programinfo.presentation.ProgramInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val programInfoModule = module {

    viewModelOf(::ProgramInfoViewModel)
}