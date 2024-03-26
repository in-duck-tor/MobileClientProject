package com.ithirteeng.secondpatternsclientproject.features.common.login.di

import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {

    viewModelOf(::LoginViewModel)
}