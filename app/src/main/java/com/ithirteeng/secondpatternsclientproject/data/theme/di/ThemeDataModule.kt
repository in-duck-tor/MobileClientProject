package com.ithirteeng.secondpatternsclientproject.data.theme.di

import com.ithirteeng.secondpatternsclientproject.data.theme.datasource.ThemeLocalDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.theme.datasource.ThemeRemoteDatasourceImpl
import com.ithirteeng.secondpatternsclientproject.data.theme.firebase.provideFirebaseDatabase
import com.ithirteeng.secondpatternsclientproject.data.theme.storage.ApplicationThemeStorage
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeLocalDatasource
import com.ithirteeng.secondpatternsclientproject.domain.theme.datasource.ThemeRemoteDatasource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val themeDataModule = module {

    singleOf(::provideFirebaseDatabase)
    singleOf(::ApplicationThemeStorage)

    single<ThemeLocalDatasource> { ThemeLocalDatasourceImpl(get()) }
    single<ThemeRemoteDatasource> { ThemeRemoteDatasourceImpl(get()) }
}