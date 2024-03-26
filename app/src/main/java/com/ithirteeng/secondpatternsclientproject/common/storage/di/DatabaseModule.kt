package com.ithirteeng.secondpatternsclientproject.common.storage.di

import com.ithirteeng.secondpatternsclientproject.common.storage.DatabaseBuilder
import org.koin.dsl.module

val databaseModule = module {

    single { DatabaseBuilder.build(context = get()) }
}
