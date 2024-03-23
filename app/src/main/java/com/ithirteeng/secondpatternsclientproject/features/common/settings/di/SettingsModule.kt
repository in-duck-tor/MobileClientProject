package com.ithirteeng.secondpatternsclientproject.features.common.settings.di

import com.ithirteeng.secondpatternsclientproject.features.common.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {

    viewModelOf(::SettingsViewModel)
}