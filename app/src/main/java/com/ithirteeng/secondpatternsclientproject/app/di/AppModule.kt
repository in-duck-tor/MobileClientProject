package com.ithirteeng.secondpatternsclientproject.app.di

import com.ithirteeng.secondpatternsclientproject.app.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MainViewModel)
}