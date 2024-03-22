package com.ithirteeng.secondpatternsclientproject.features.client.settings.di

import com.ithirteeng.secondpatternsclientproject.features.client.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {

    viewModelOf(::SettingsViewModel)
}