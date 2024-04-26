package com.ithirteeng.secondpatternsclientproject.features.common.login.di

import com.ithirteeng.secondpatternsclientproject.features.common.auth.presentation.AuthViewModel
import com.ithirteeng.secondpatternsclientproject.features.common.login.auth.AuthManager
import com.ithirteeng.secondpatternsclientproject.features.common.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val loginModule = module {

    viewModelOf(::LoginViewModel)
    viewModelOf(::AuthViewModel)
    singleOf(::AuthManager)
}