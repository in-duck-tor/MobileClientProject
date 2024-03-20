package com.ithirteeng.secondpatternsclientproject.data.theme.di

import com.ithirteeng.secondpatternsclientproject.data.theme.datasource.ThemeLocalDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.theme.storage.ApplicationThemeStorage
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeLocalDatasource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val themeDataModule = module {

    singleOf(::ApplicationThemeStorage)

    single<ThemeLocalDatasource> { ThemeLocalDatasourceImpl(get()) }
}