package com.ithirteeng.secondpatternsclientproject.features.common.registration.di

import com.ithirteeng.secondpatternsclientproject.features.common.registration.presentation.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {

    viewModel {
        RegistrationViewModel()
    }
}
